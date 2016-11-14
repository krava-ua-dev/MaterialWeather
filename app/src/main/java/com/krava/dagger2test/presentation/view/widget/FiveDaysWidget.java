package com.krava.dagger2test.presentation.view.widget;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.krava.dagger2test.R;
import com.krava.dagger2test.presentation.Global;
import com.krava.dagger2test.presentation.model.CurrentDayWeatherResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by krava2008 on 27.10.16.
 */

public class FiveDaysWidget {
    private LinearLayout root;
    private View[] dayViews;
    private LayoutInflater inflater;


    public FiveDaysWidget(LinearLayout root){
        this.root = root;
        this.inflater = LayoutInflater.from(root.getContext());
        this.dayViews = new View[5];

        initViews();
    }

    private void initViews() {
        root.removeAllViews();
        for(int i = 0; i < 5; i++) {
            View view = inflater.inflate(R.layout.value_of_hour_view, null);
            if(i != 4) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, Global.dp(16), 0);
                view.setLayoutParams(params);
            }
            dayViews[i] = view;
            root.addView(dayViews[i]);
        }
    }

    public void initValues(List<CurrentDayWeatherResponse> weatherResponses) {
        for(int i = 0; i < 5; i++){
            CurrentDayWeatherResponse currentDay = weatherResponses.get(i);
            Global.setWeatherIconByState(currentDay.getWeathers().get(0).getDescription(), (ImageView) dayViews[i].findViewById(R.id.temp_icon));
            setDateToView(dayViews[i], currentDay.getDate() * 1000);
            setTempToView(dayViews[i], currentDay.getMain().getTemp());
        }
    }

    @SuppressLint("DefaultLocale")
    private void setTempToView(View view, double temp){
        ((TextView) view.findViewById(R.id.temp)).setText(String.format("%.0f°", temp - 273.15f));
    }

    private void setDateToView(View view, long date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("hh a");
        ((TextView) view.findViewById(R.id.day_title)).setText(dateFormat.format(new Date(date)));
    }


}
