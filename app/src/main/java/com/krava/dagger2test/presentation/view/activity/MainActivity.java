package com.krava.dagger2test.presentation.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.krava.dagger2test.R;
import com.krava.dagger2test.presentation.di.HasComponent;
import com.krava.dagger2test.presentation.di.components.CityComponent;
import com.krava.dagger2test.presentation.di.components.DaggerCityComponent;
import com.krava.dagger2test.presentation.view.fragment.WeatherForCityFragment;
import com.krava.dagger2test.presentation.view.fragment.DetectCityFragment;


public class MainActivity extends BaseActivity implements HasComponent<CityComponent>, DetectCityFragment.CityDetectionListener{

    private CityComponent cityComponent;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.color.colorPrimary);

        city = PreferenceManager.getDefaultSharedPreferences(this).getString("default_city", "");

        initilizeInjector();

        if(city.equals("")) {
            addFragment(R.id.fragment_wrapper, new DetectCityFragment(), "detect_city");
        }else{
            addFragment(R.id.fragment_wrapper,  WeatherForCityFragment.getInstance(city), "city_fragment");
        }
    }

    private void initilizeInjector() {
        this.cityComponent = DaggerCityComponent.builder()
                .activityModule(getActivityModule())
                .weatherModule(getWeatherModule(city, 5))
                .applicationComponent(getAppComponent())
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == DetectCityFragment.REQUEST_CHECK_SETTINGS) {
            ((DetectCityFragment)getSupportFragmentManager().findFragmentByTag("detect_city"))
                    .onGpsRequestResult(resultCode == RESULT_OK);
        }
    }


    @Override
    public CityComponent getComponent() {
        return cityComponent;
    }

    @Override
    public void onCityDetected(String cityName) {
        this.city = cityName;
        initilizeInjector();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("default_city", cityName);
        editor.apply();

        addFragment(R.id.fragment_wrapper,  WeatherForCityFragment.getInstance(cityName), "city_fragment");
    }
}
