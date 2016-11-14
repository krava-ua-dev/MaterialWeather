package com.krava.dagger2test.data.cache;

import android.content.Context;
import android.util.Log;

import com.krava.dagger2test.data.exception.WeatherNotFoundException;
import com.krava.dagger2test.domain.executor.ThreadExecutor;
import com.krava.dagger2test.presentation.model.CurrentDayWeatherResponse;
import com.krava.dagger2test.presentation.model.DailyWeatherResponse;
import com.krava.dagger2test.presentation.model.FiveDaysWeatherObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by krava2008 on 04.11.16.
 */

public class WeatherCacheImpl implements WeatherCache {
    private static final String SETTINGS_FILE_NAME = "com.krava.dagger2.weather";
    private static final String FORMAT_KEY_LAST_CACHE_UPDATE = "%s_%s";

    private static final String DEFAULT_FILE_NAME = "city_";
    public static final String TYPE_FORECAST = "forecast";
    public static final String TYPE_DAILY = "daily";
    public static final String TYPE_CURRENT = "current";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final ThreadExecutor threadExecutor;
    private final FileManager fileManager;
    private final File cacheDir;


    @Inject
    public WeatherCacheImpl(Context context, FileManager fileManager, ThreadExecutor executor) {
        if (context == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context;
        this.threadExecutor = executor;
        this.fileManager = fileManager;
        this.cacheDir = this.context.getCacheDir();
    }


    @Override
    public Observable<FiveDaysWeatherObject> getForecast(String cityName) {
        return Observable.create(subscriber -> {
            File userEntityFile = WeatherCacheImpl.this.buildFile(cityName, TYPE_FORECAST);
            String fileContent = WeatherCacheImpl.this.fileManager.readFileContent(userEntityFile);
            FiveDaysWeatherObject weatherObject;
            try {
                weatherObject = new FiveDaysWeatherObject(new JSONObject(fileContent));
            }catch (JSONException exc) {
                weatherObject = null;
            }

            if (weatherObject != null) {
                subscriber.onNext(weatherObject);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new WeatherNotFoundException());
            }
        });
    }

    @Override
    public Observable<CurrentDayWeatherResponse> getCurrent(String cityName) {
        return Observable.create(subscriber -> {
            File userEntityFile = WeatherCacheImpl.this.buildFile(cityName, TYPE_CURRENT);
            String fileContent = WeatherCacheImpl.this.fileManager.readFileContent(userEntityFile);
            CurrentDayWeatherResponse weatherObject;
            try {
                weatherObject = new CurrentDayWeatherResponse(new JSONObject(fileContent));
            }catch (JSONException exc) {
                weatherObject = null;
            }

            if (weatherObject != null) {
                subscriber.onNext(weatherObject);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new WeatherNotFoundException());
            }
        });
    }

    @Override
    public Observable<DailyWeatherResponse> getDaily(String cityName) {
        return Observable.create(subscriber -> {
            File userEntityFile = WeatherCacheImpl.this.buildFile(cityName, TYPE_DAILY);
            String fileContent = WeatherCacheImpl.this.fileManager.readFileContent(userEntityFile);
            DailyWeatherResponse weatherObject;
            try {
                weatherObject = new DailyWeatherResponse(new JSONObject(fileContent));
            }catch (JSONException exc) {
                weatherObject = null;
            }

            if (weatherObject != null) {
                subscriber.onNext(weatherObject);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new WeatherNotFoundException());
            }
        });
    }

    @Override
    public void put(String cityName, String weather, String type) {
        if(weather != null) {
            File weatherFile = buildFile(cityName, type);
            this.executeAsynchronously(new CacheWriter(this.fileManager, weatherFile, weather));
            setLastCacheUpdateTimeMillis(cityName, type);
        }
    }

    @Override
    public boolean isCached(String cityName, String type) {
        File weatherFile = this.buildFile(cityName, type);
        return this.fileManager.exists(weatherFile);
    }

    @Override
    public boolean isExpired(String city, String type) {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis(city, type);

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
    }

    private void setLastCacheUpdateTimeMillis(String cityName, String type) {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                String.format(FORMAT_KEY_LAST_CACHE_UPDATE, cityName, type),
                currentMillis
        );
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis(String cityName, String type) {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                String.format(FORMAT_KEY_LAST_CACHE_UPDATE, cityName, type)
        );
    }

    private File buildFile(String cityName, String type) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(cityName);
        fileNameBuilder.append("_");
        fileNameBuilder.append(type);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}
