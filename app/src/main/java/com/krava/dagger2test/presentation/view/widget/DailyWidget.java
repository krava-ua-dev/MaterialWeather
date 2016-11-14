package com.krava.dagger2test.presentation.view.widget;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.krava.dagger2test.R;
import com.krava.dagger2test.presentation.Global;
import com.krava.dagger2test.presentation.model.DailyWeatherObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by krava2008 on 27.10.16.
 */

public class DailyWidget {
    private LineChart chart;
    private LinearLayout iconsWrapper;
    private View[] iconsViews;
    private LayoutInflater inflater;


    public DailyWidget(LineChart chart, LinearLayout iconsWrapper){
        this.chart = chart;
        this.chart.setDescription(null);

        this.iconsWrapper = iconsWrapper;
        this.iconsViews = new View[5];
        this.inflater = LayoutInflater.from(iconsWrapper.getContext());
    }

    public void setData(List<DailyWeatherObject> list){
        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXESday = new ArrayList<>();
        ArrayList<Entry> yAXESnight = new ArrayList<>();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd.MM");

        int i = 0;
        iconsWrapper.removeAllViews();
        for(DailyWeatherObject object : list) {
            float yd = (float)Math.round(object.getTemp().getDay() - 273.15f);
            float yn = (float)Math.round(object.getTemp().getNight() - 273.15f);

            yAXESday.add(new Entry(object.getDate(), yd));
            yAXESnight.add(new Entry(object.getDate(), yn));

            xAXES.add(format.format(new Date(object.getDate())));

            initIconWithDate(i, object);
            i++;
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        LineDataSet lineDataSet = new LineDataSet(yAXESday, "day");
        lineDataSet.setColor(Color.parseColor("#FF9800"));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format("%.0f°", value);
            }
        });
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setCircleColor(Color.parseColor("#FF9800"));
        lineDataSet.setCircleRadius(3f);

        LineDataSet lineDataSet1 = new LineDataSet(yAXESnight, "night");
        lineDataSet1.setColor(Color.parseColor("#a7a7a7"));
        lineDataSet1.setDrawCircleHole(false);
        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet1.setDrawHighlightIndicators(false);
        lineDataSet1.setCircleColor(Color.parseColor("#a7a7a7"));
        lineDataSet1.setCircleRadius(3f);
        lineDataSet1.setValueFormatter(new IValueFormatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format("%.0f°", value);
            }
        });

        dataSets.add(lineDataSet);
        dataSets.add(lineDataSet1);

        chart.setData(new LineData(dataSets));

        chart.getLegend().setEnabled(false);
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawLabels(false);
        yAxis.setDrawTopYLabelEntry(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawLimitLinesBehindData(false);
        yAxis.setDrawZeroLine(true);

        yAxis = chart.getAxisRight();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawLabels(false);
        yAxis.setDrawTopYLabelEntry(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawLimitLinesBehindData(false);
        yAxis.setDrawZeroLine(true);

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawLimitLinesBehindData(false);
        chart.getXAxis().setDrawLabels(false);
        chart.getXAxis().setDrawAxisLine(false);
    }

    private void initIconWithDate(int position, DailyWeatherObject object) {
        View view = inflater.inflate(R.layout.value_of_hour_view, null);
        if(position != 4) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, Global.dp(16), 0);
            view.setLayoutParams(params);
        }
        iconsViews[position] = view;

        iconsWrapper.addView(view);

        view.findViewById(R.id.day_title).setVisibility(View.GONE);
        Global.setWeatherIconByState(object.getWeather().get(0).getDescription(), (ImageView)view.findViewById(R.id.temp_icon));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
        ((TextView) view.findViewById(R.id.temp)).setText(dateFormat.format(new Date(object.getDate() * 1000)));
    }
}
