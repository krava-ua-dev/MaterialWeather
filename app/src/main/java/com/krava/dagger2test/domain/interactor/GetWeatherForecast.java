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

public class GetWeatherForecast extends UseCase {
    private final String cityName;
    private final WeatherRepository weatherRepository;

    @Inject
    public GetWeatherForecast(String cityName, WeatherRepository weatherRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);

        this.weatherRepository = weatherRepository;
        this.cityName = cityName;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        Log.e("GetWeatherForecast", "city: " + cityName);
        return this.weatherRepository.forecast(cityName);
    }
}
