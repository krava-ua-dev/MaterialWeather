package com.krava.dagger2test.presentation.model;

import org.json.JSONObject;

/**
 * Created by krava2008 on 27.10.16.
 */

public class TempWeatherObject {
    private double day;
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;

    public TempWeatherObject(JSONObject jsonObject) {
        parse(jsonObject);
    }

    private void parse(JSONObject jsonObject) {
        day = jsonObject.optDouble("day");
        min = jsonObject.optDouble("min");
        max = jsonObject.optDouble("max");
        night = jsonObject.optDouble("night");
        eve = jsonObject.optDouble("eve");
        morn = jsonObject.optDouble("morn");
    }

    public double getDay() {
        return day;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getNight() {
        return night;
    }

    public double getEve() {
        return eve;
    }

    public double getMorn() {
        return morn;
    }
}

