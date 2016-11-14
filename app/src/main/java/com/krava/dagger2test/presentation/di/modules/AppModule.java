package com.krava.dagger2test.presentation.di.modules;

import android.app.Application;
import android.content.Context;

import com.krava.dagger2test.data.cache.WeatherCache;
import com.krava.dagger2test.data.cache.WeatherCacheImpl;
import com.krava.dagger2test.data.executor.JobExecutor;
import com.krava.dagger2test.data.repository.WeatherDataRepository;
import com.krava.dagger2test.domain.executor.PostExecutionThread;
import com.krava.dagger2test.domain.executor.ThreadExecutor;
import com.krava.dagger2test.domain.repository.WeatherRepository;
import com.krava.dagger2test.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by krava2008 on 30.10.16.
 */

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplication() {
        return application;
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    WeatherCache provideWeatherCache(WeatherCacheImpl weatherCache) {
        return weatherCache;
    }

    @Provides @Singleton
    WeatherRepository provideWeatherRepository(WeatherDataRepository weatherDataRepository) {
        return weatherDataRepository;
    }
}
