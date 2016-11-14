package com.krava.dagger2test.presentation.di.components;

import android.app.Activity;

import com.krava.dagger2test.presentation.di.modules.ActivityModule;
import com.krava.dagger2test.presentation.di.scopes.PerActivity;

import dagger.Component;

/**
 * Created by krava2008 on 31.10.16.
 */

@PerActivity
@Component (dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
