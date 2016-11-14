package com.krava.dagger2test.presentation.di.modules;

import android.app.Activity;

import com.krava.dagger2test.presentation.di.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by krava2008 on 31.10.16.
 */

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
