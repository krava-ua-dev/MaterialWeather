package com.krava.dagger2test.presentation;

import android.app.Application;

import com.krava.dagger2test.presentation.di.components.ApplicationComponent;
import com.krava.dagger2test.presentation.di.components.DaggerApplicationComponent;
import com.krava.dagger2test.presentation.di.modules.AppModule;
import com.krava.dagger2test.presentation.di.modules.NetModule;


/**
 * Created by krava2008 on 18.10.16.
 */

public class WeatherApplication extends Application {
    private ApplicationComponent appComponent;
    public static final String APIKEY = "cfc2327e210f73c0a331759a8462d67c";

    @Override
    public void onCreate() {
        super.onCreate();

        Global.setApplicationContext(getApplicationContext());
        appComponent = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://api.openweathermap.org/data/2.5/"))
                .build();
    }

    public ApplicationComponent getAppComponent(){
        return appComponent;
    }
}

