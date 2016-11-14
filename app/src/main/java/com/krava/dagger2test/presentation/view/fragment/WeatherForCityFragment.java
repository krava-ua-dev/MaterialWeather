package com.krava.dagger2test.presentation.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoTextView;
import com.github.mikephil.charting.charts.LineChart;
import com.krava.dagger2test.R;
import com.krava.dagger2test.databinding.FragmentCityWeatherBinding;
import com.krava.dagger2test.presentation.Global;
import com.krava.dagger2test.presentation.di.components.CityComponent;
import com.krava.dagger2test.presentation.model.CurrentDayWeatherResponse;
import com.krava.dagger2test.presentation.model.DailyWeatherResponse;
import com.krava.dagger2test.presentation.model.FiveDaysWeatherObject;
import com.krava.dagger2test.presentation.presenter.WeatherForCityPresenter;
import com.krava.dagger2test.presentation.view.CityWeatherView;
import com.krava.dagger2test.presentation.view.behavoir.MainWeatherTextBehavior;
import com.krava.dagger2test.presentation.view.widget.DailyWidget;
import com.krava.dagger2test.presentation.view.widget.FiveDaysWidget;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by krava2008 on 31.10.16.
 */

public class WeatherForCityFragment extends BaseFragment implements CityWeatherView,
        RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    @Inject
    WeatherForCityPresenter presenter;

    private FragmentCityWeatherBinding binding;
    private MainWeatherTextBehavior cityBehavoir;
    private MainWeatherTextBehavior descBehevoir;
    private FiveDaysWidget fiveDaysWidget;
    private DailyWidget dailyWidget;
    private RapidFloatingActionHelper rfabHelper;


    public WeatherForCityFragment(){ setRetainInstance(true); }

    public static WeatherForCityFragment getInstance(String cityName) {
        Bundle bundle = new Bundle();
        bundle.putString("city_name", cityName);

        WeatherForCityFragment fragment = new WeatherForCityFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getComponent(CityComponent.class).inject(this);

        Bundle args = getArguments();
        if(args != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCityWeatherBinding.inflate(inflater, container, false);

        initControls();
        initFabs(getActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
        if(savedInstanceState == null) {
            presenter.loadWeatherToday();
            presenter.loadForecastWeather();
            presenter.loadDailyWeather();
        }
    }

    private void initControls() {
        fiveDaysWidget = new FiveDaysWidget((LinearLayout) binding.getRoot().findViewById(R.id.days_wrapper));
        dailyWidget = new DailyWidget(
                (LineChart) binding.getRoot().findViewById(R.id.chart),
                (LinearLayout) binding.getRoot().findViewById(R.id.ds_wrapper)
        );
    }

    private void initFabs(Context context){
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("List of cities")
                .setResId(R.drawable.ic_city_white_24dp)
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.WHITE)
                .setLabelSizeSp(14)
                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(context, 4)))
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Add new city")
                .setResId(R.drawable.ic_map_marker_plus_white_24dp)
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.WHITE)
                .setLabelSizeSp(14)
                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(context, 4)))
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Settings")
                .setResId(R.drawable.ic_settings_white_24dp)
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.WHITE)
                .setLabelSizeSp(14)
                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(context, 4)))
                .setWrapper(2)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(context, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(context, 5))
        ;
        rfabHelper = new RapidFloatingActionHelper(
                context,
                binding.activityMainRfal,
                binding.activityMainRfab,
                rfaContent
        ).build();
    }


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onCurrentWeatherLoaded(CurrentDayWeatherResponse response) {
        binding.mainCity.setText(String.format("%s, %s", response.getName(), response.getSys().getCountry().toUpperCase()));
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) binding.mainCity.getLayoutParams();
            cityBehavoir = new MainWeatherTextBehavior(binding.mainCity.getContext(), null);
            lp.setBehavior(cityBehavoir);
            binding.mainCity.setLayoutParams(lp);
            cityBehavoir.onDependentViewChanged(binding.root, binding.mainCity, binding.toolbar);
        },100);
        String desc = response.getWeathers().get(0).getDescription();
        Global.setWeatherIconByState(desc, binding.mainWeatherIcon);
        binding.mainState.setText(desc.substring(0, 1).toUpperCase() + desc.substring(1));
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            CoordinatorLayout.LayoutParams dlp = (CoordinatorLayout.LayoutParams) binding.mainState.getLayoutParams();
            descBehevoir = new MainWeatherTextBehavior(binding.mainState.getContext(), null);
            dlp.setBehavior(descBehevoir);
            binding.mainState.setLayoutParams(dlp);
            descBehevoir.onDependentViewChanged(binding.root, binding.mainState, binding.toolbar);
        }, 100);
        binding.mainValue.setText(String.format("%.0f°", response.getMain().getTemp() - 273.15f));

        ((TextView) binding.root.findViewById(R.id.wind_value)).setText(
                String.format("%.0f m/s, %.0f°", response.getWind().getSpeed(), response.getWind().getDeg())
        );
        ((TextView) binding.root.findViewById(R.id.clouds_value)).setText(
                String.format("%d°", response.getClouds())
        );
        ((TextView) binding.root.findViewById(R.id.humidity_value)).setText(
                String.format("%d°", response.getMain().getHumidity())
        );
    }

    @Override
    public void onHourlyWeatherLoaded(FiveDaysWeatherObject response) {
        fiveDaysWidget.initValues(response.getWeathers());
    }

    @Override
    public void onDailyWeatherLoaded(DailyWeatherResponse response) {
        dailyWidget.setData(response.getList());
    }

    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {

    }

    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {

    }
}
