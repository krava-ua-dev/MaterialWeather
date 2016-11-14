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

public class GetWeatherDaily extends UseCase {

    private final String cityName;
    private final int dayCount;
    private final WeatherRepository weatherRepository;

    @Inject
    public GetWeatherDaily(WeatherRepository repository, String cityName, int dayCount,
                           ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);

        this.cityName = cityName;
        this.weatherRepository = repository;
        this.dayCount = dayCount;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        Log.e("GetWeatherDaily", "city: " + cityName);
        return this.weatherRepository.daily(cityName, dayCount);
    }
}
