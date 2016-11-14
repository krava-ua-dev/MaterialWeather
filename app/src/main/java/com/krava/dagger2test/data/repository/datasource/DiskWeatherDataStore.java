package com.krava.dagger2test.data.repository.datasource;

import com.krava.dagger2test.data.cache.WeatherCache;
import com.krava.dagger2test.presentation.model.CurrentDayWeatherResponse;
import com.krava.dagger2test.presentation.model.DailyWeatherResponse;
import com.krava.dagger2test.presentation.model.FiveDaysWeatherObject;

import rx.Observable;

/**
 * Created by krava2008 on 04.11.16.
 */

public class DiskWeatherDataStore implements WeatherDataStore {

    private final WeatherCache weatherCache;

    DiskWeatherDataStore(WeatherCache weatherCache) {
        this.weatherCache = weatherCache;
    }

    @Override
    public Observable<CurrentDayWeatherResponse> current(String cityName) {
        return this.weatherCache.getCurrent(cityName);
    }

    @Override
    public Observable<FiveDaysWeatherObject> forecast(String cityName) {
        return weatherCache.getForecast(cityName);
    }

    @Override
    public Observable<DailyWeatherResponse> daily(String cityName, int dayCount) {
        return weatherCache.getDaily(cityName);
    }
}
