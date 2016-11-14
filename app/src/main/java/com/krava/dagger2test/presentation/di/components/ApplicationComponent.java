package com.krava.dagger2test.presentation.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.krava.dagger2test.domain.executor.PostExecutionThread;
import com.krava.dagger2test.domain.executor.ThreadExecutor;
import com.krava.dagger2test.domain.repository.WeatherRepository;
import com.krava.dagger2test.presentation.di.modules.AppModule;
import com.krava.dagger2test.presentation.di.modules.NetModule;
import com.krava.dagger2test.presentation.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by krava2008 on 31.10.16.
 */

@Singleton
@Component (modules = {AppModule.class, NetModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity activity);

    Context context();
    Retrofit retrofit();
    SharedPreferences sharedPreferences();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    WeatherRepository weatherRepository();
}
