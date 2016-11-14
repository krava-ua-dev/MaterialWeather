package com.krava.dagger2test.domain.interactor;

import android.util.Log;

import com.krava.dagger2test.domain.executor.PostExecutionThread;
import com.krava.dagger2test.domain.executor.ThreadExecutor;
import com.krava.dagger2test.domain.repository.WeatherRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by krava2008 on 04.11.16.
 */

public class GetWeatherToday extends UseCase {
    private final String cityName;
    private final WeatherRepository weatherRepository;

    @Inject
    public GetWeatherToday(WeatherRepository repository, String cityName,
                              ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);

        this.cityName = cityName;
        this.weatherRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        Log.e("GetWeatherToday", "city: " + cityName);
        return this.weatherRepository.current(cityName);
    }
}
