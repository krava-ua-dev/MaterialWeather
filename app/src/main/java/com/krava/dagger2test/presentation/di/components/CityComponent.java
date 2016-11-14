package com.krava.dagger2test.presentation.di.components;

import com.krava.dagger2test.presentation.di.modules.ActivityModule;
import com.krava.dagger2test.presentation.di.modules.WeatherModule;
import com.krava.dagger2test.presentation.di.scopes.PerActivity;
import com.krava.dagger2test.presentation.view.fragment.CityListFragment;
import com.krava.dagger2test.presentation.view.fragment.WeatherForCityFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, WeatherModule.class})
public interface CityComponent extends ActivityComponent {
  void inject(WeatherForCityFragment weatherForCityFragment);
  void inject(CityListFragment cityListFragment);
}
