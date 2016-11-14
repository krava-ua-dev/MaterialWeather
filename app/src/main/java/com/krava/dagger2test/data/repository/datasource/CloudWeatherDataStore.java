package com.krava.dagger2test.data.repository.datasource;

import com.krava.dagger2test.data.cache.WeatherCache;
import com.krava.dagger2test.network.interfaces.WeatherApiInterface;
import com.krava.dagger2test.presentation.WeatherApplication;
import com.krava.dagger2test.presentation.model.CurrentDayWeatherResponse;
import com.krava.dagger2test.presentation.model.DailyWeatherResponse;
import com.krava.dagger2test.presentation.model.FiveDaysWeatherObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Retrofit;
import rx.Observable;

import static com.krava.dagger2test.data.cache.WeatherCacheImpl.TYPE_CURRENT;
import static com.krava.dagger2test.data.cache.WeatherCacheImpl.TYPE_DAILY;
import static com.krava.dagger2test.data.cache.WeatherCacheImpl.TYPE_FORECAST;

/**
 * Created by krava2008 on 04.11.16.
 */

public class CloudWeatherDataStore implements WeatherDataStore {
    private WeatherApiInterface weatherApiInterface;
    private WeatherCache weatherCache;


    CloudWeatherDataStore(Retrofit retrofit, WeatherCache weatherCache) {
        this.weatherApiInterface = retrofit.create(WeatherApiInterface.class);
        this.weatherCache = weatherCache;
    }


    @Override
    public Observable<CurrentDayWeatherResponse> current(final String cityName) {
        return weatherApiInterface.getForDayByCityName(cityName, WeatherApplication.APIKEY)
                .flatMap(response -> {
                    try {
                        String responseString = response.body().string();
                        weatherCache.put(cityName, responseString, TYPE_CURRENT);

                        return Observable.just(new CurrentDayWeatherResponse(
                                new JSONObject(responseString)
                        ));
                    } catch (JSONException | IOException exc) {
                        exc.printStackTrace();
                        return Observable.empty();
                    }
                });
    }

    @Override
    public Observable<FiveDaysWeatherObject> forecast(final String cityName) {
        return weatherApiInterface.forecast(cityName, WeatherApplication.APIKEY)
                .flatMap(response -> {
                    try{
                        String responseString = response.body().string();
                        weatherCache.put(cityName, responseString, TYPE_FORECAST);

                        return Observable.just(new FiveDaysWeatherObject(
                                new JSONObject(responseString)
                        ));
                    }catch (JSONException | IOException exc) {
                        return Observable.empty();
                    }
                });
    }

    @Override
    public Observable<DailyWeatherResponse> daily(final String cityName, int dayCount) {
        return this.weatherApiInterface.daily(cityName, dayCount, WeatherApplication.APIKEY)
                .flatMap(response -> {
                    try{
                        String responseString = response.body().string();
                        weatherCache.put(cityName, responseString, TYPE_DAILY);

                        return Observable.just(new DailyWeatherResponse(
                                new JSONObject(responseString)
                        ));
                    }catch (JSONException | IOException exc) {
                        return Observable.empty();
                    }
                });
    }
}
