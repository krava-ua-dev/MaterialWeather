package com.krava.dagger2test.data.repository.datasource;

import android.support.annotation.NonNull;
import android.util.Log;

import com.krava.dagger2test.data.cache.WeatherCache;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;


/**
 * Created by krava2008 on 04.11.16.
 */
@Singleton
public class WeatherDataStoreFactory {
    private final Retrofit retrofit;
    private final WeatherCache weatherCache;

    @Inject
    public WeatherDataStoreFactory(@NonNull Retrofit retrofit, @NonNull WeatherCache weatherCache) {
        this.retrofit = retrofit;
        this.weatherCache = weatherCache;
    }

    public WeatherDataStore create(String cityName, String type) {
        WeatherDataStore weatherDataStore;

        if(!this.weatherCache.isExpired(cityName, type) && this.weatherCache.isCached(cityName, type)) {
            Log.e("WeatherDataStoreFactory", "weather from cache");
            weatherDataStore = new DiskWeatherDataStore(weatherCache);
        }else{
            Log.e("WeatherDataStoreFactory", "weather from cloud");
            weatherDataStore = createCloudDataStore();
        }

        return weatherDataStore;
    }

    public WeatherDataStore createCloudDataStore() {
        return new CloudWeatherDataStore(retrofit, weatherCache);
    }
}
