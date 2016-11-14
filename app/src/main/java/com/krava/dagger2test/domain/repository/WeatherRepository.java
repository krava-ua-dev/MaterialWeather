package com.krava.dagger2test.domain.repository;

import com.krava.dagger2test.presentation.model.CurrentDayWeatherResponse;
import com.krava.dagger2test.presentation.model.DailyWeatherResponse;
import com.krava.dagger2test.presentation.model.FiveDaysWeatherObject;

import rx.Observable;

/**
 * Created by krava2008 on 04.11.16.
 */

public interface WeatherRepository {
    Observable<CurrentDayWeatherResponse> current(String cityName);
    Observable<FiveDaysWeatherObject> forecast(String cityName);
    Observable<DailyWeatherResponse> daily(String cityName, int dayCount);
}
