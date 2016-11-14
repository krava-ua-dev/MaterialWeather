package com.krava.dagger2test.presentation.view.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.krava.dagger2test.presentation.WeatherApplication;
import com.krava.dagger2test.presentation.di.components.ApplicationComponent;
import com.krava.dagger2test.presentation.di.modules.ActivityModule;
import com.krava.dagger2test.presentation.di.modules.WeatherModule;

/**
 * Created by krava2008 on 31.10.16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getAppComponent().inject(this);
    }

    protected void addFragment(int containerViewId, Fragment fragment, String tag) {
        getSupportFragmentManager()
            .beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .replace(containerViewId, fragment, tag)
            .commit();
    }

    protected ApplicationComponent getAppComponent(){
        return ((WeatherApplication) getApplication()).getAppComponent();
    }

    protected WeatherModule getWeatherModule(String cityName, int dayCount) {
        return new WeatherModule(cityName, dayCount);
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
