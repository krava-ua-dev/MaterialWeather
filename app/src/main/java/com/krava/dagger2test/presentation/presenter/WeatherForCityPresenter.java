package com.krava.dagger2test.presentation.presenter;


import android.support.annotation.NonNull;
import android.util.Log;

import com.krava.dagger2test.domain.interactor.UseCase;
import com.krava.dagger2test.presentation.di.scopes.PerActivity;
import com.krava.dagger2test.presentation.model.CurrentDayWeatherResponse;
import com.krava.dagger2test.presentation.model.DailyWeatherResponse;
import com.krava.dagger2test.presentation.model.FiveDaysWeatherObject;
import com.krava.dagger2test.presentation.view.CityWeatherView;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Subscriber;

/**
 * Created by krava2008 on 31.10.16.
 */

@PerActivity
public class WeatherForCityPresenter implements Presenter {

    private CityWeatherView weatherView;

    private UseCase getWeatherTodayUseCase;
    private UseCase getWeatherForecastUseCase;
    private UseCase getWeatherDailyUseCase;

    @Inject public WeatherForCityPresenter(@Named("today") UseCase getWeatherTodayUseCase,
                                           @Named("forecast") UseCase getWeatherForecastUseCase,
                                           @Named("daily") UseCase getWeatherDailyUseCase) {

        this.getWeatherDailyUseCase = getWeatherDailyUseCase;
        this.getWeatherForecastUseCase = getWeatherForecastUseCase;
        this.getWeatherTodayUseCase = getWeatherTodayUseCase;
    }

    public void setView(@NonNull CityWeatherView view) {
        this.weatherView = view;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        this.getWeatherTodayUseCase.unsubscribe();
        this.getWeatherForecastUseCase.unsubscribe();
        this.getWeatherDailyUseCase.unsubscribe();

        this.weatherView = null;
    }

    public void loadDailyWeather() {
        this.getWeatherDailyUseCase.execute(new Subscriber<DailyWeatherResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(DailyWeatherResponse dailyWeatherResponse) {
                Log.e("WeatherPresenter", "loadDailyWeather");
                weatherView.onDailyWeatherLoaded(dailyWeatherResponse);
            }
        });
    }

    public void loadForecastWeather(){
        this.getWeatherForecastUseCase.execute(new Subscriber<FiveDaysWeatherObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(FiveDaysWeatherObject fiveDaysWeatherObject) {
                Log.e("WeatherPresenter", "loadForecastWeather");
                weatherView.onHourlyWeatherLoaded(fiveDaysWeatherObject);
            }
        });
    }

    public void loadWeatherToday(){
        this.getWeatherTodayUseCase.execute(new Subscriber<CurrentDayWeatherResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(CurrentDayWeatherResponse currentDayWeatherResponse) {
                Log.e("WeatherPresenter", "loadWeatherToday");
                weatherView.onCurrentWeatherLoaded(currentDayWeatherResponse);
            }
        });
    }
}
