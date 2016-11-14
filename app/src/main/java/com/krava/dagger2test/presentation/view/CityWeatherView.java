package com.krava.dagger2test.presentation.view;

import com.krava.dagger2test.presentation.model.CurrentDayWeatherResponse;
import com.krava.dagger2test.presentation.model.DailyWeatherResponse;
import com.krava.dagger2test.presentation.model.FiveDaysWeatherObject;

/**
 * Created by krava2008 on 31.10.16.
 */

public interface CityWeatherView {
    void onCurrentWeatherLoaded(CurrentDayWeatherResponse response);
    void onHourlyWeatherLoaded(FiveDaysWeatherObject response);
    void onDailyWeatherLoaded(DailyWeatherResponse response);
}
