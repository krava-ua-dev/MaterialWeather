package com.krava.dagger2test.data.repository;

import com.krava.dagger2test.data.repository.datasource.WeatherDataStoreFactory;
import com.krava.dagger2test.domain.repository.WeatherRepository;
import com.krava.dagger2test.presentation.model.CurrentDayWeatherResponse;
import com.krava.dagger2test.presentation.model.DailyWeatherResponse;
import com.krava.dagger2test.presentation.model.FiveDaysWeatherObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static com.krava.dagger2test.data.cache.WeatherCacheImpl.TYPE_CURRENT;
import static com.krava.dagger2test.data.cache.WeatherCacheImpl.TYPE_DAILY;
import static com.krava.dagger2test.data.cache.WeatherCacheImpl.TYPE_FORECAST;

/**
 * Created by krava2008 on 04.11.16.
 */
@Singleton
public class WeatherDataRepository implements WeatherRepository {

    private final WeatherDataStoreFactory dataStoreFactory;

    @Inject
    public WeatherDataRepository(WeatherDataStoreFactory dataStoreFactory) {
        this.dataStoreFactory = dataStoreFactory;
    }

    @Override
    public Observable<CurrentDayWeatherResponse> current(String cityName) {
        return dataStoreFactory.create(cityName, TYPE_CURRENT).current(cityName);
    }

    @Override
    public Observable<FiveDaysWeatherObject> forecast(String cityName) {
        return dataStoreFactory.create(cityName, TYPE_FORECAST).forecast(cityName);
    }

    @Override
    public Observable<DailyWeatherResponse> daily(String cityName, int dayCount) {
        return dataStoreFactory.create(cityName, TYPE_DAILY).daily(cityName, dayCount);
    }
}
