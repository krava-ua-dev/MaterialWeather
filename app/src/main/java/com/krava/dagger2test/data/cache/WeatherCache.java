package com.krava.dagger2test.data.cache;

import com.krava.dagger2test.presentation.model.CurrentDayWeatherResponse;
import com.krava.dagger2test.presentation.model.DailyWeatherResponse;
import com.krava.dagger2test.presentation.model.FiveDaysWeatherObject;

import rx.Observable;

/**
 * Created by krava2008 on 04.11.16.
 */

public interface WeatherCache {
    Observable<FiveDaysWeatherObject> getForecast(final String cityName);

    Observable<CurrentDayWeatherResponse> getCurrent(final String cityName);

    Observable<DailyWeatherResponse> getDaily(final String cityName);

    void put(String cityName, String weather, String type);

    boolean isCached(final String cityName, String type);

    boolean isExpired(String cityName, String type);

    void evictAll();
}
