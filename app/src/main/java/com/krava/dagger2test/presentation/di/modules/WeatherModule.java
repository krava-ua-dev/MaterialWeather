package com.krava.dagger2test.presentation.di.modules;


import com.krava.dagger2test.domain.executor.PostExecutionThread;
import com.krava.dagger2test.domain.executor.ThreadExecutor;
import com.krava.dagger2test.domain.interactor.GetWeatherDaily;
import com.krava.dagger2test.domain.interactor.GetWeatherForecast;
import com.krava.dagger2test.domain.interactor.GetWeatherToday;
import com.krava.dagger2test.domain.interactor.UseCase;
import com.krava.dagger2test.domain.repository.WeatherRepository;
import com.krava.dagger2test.presentation.di.scopes.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by krava2008 on 30.10.16.
 */

@Module
public class WeatherModule {

    private String cityName;
    private int dayCount;


    public WeatherModule(String cityName, int dayCount) {
        this.cityName = cityName;
        this.dayCount = dayCount;
    }


    @Provides @PerActivity
    @Named("daily") UseCase provideGetWeatherDailyUseCase(
            WeatherRepository weatherRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new GetWeatherDaily(weatherRepository, cityName, dayCount, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity
    @Named("forecast") UseCase provideGetWeatherForecastUseCase(
            WeatherRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new GetWeatherForecast(cityName, userRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity
    @Named("today") UseCase provideGetWeatherTodayUseCase(
            WeatherRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new GetWeatherToday(userRepository, cityName, threadExecutor, postExecutionThread);
    }
}
