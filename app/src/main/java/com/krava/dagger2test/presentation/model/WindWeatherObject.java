package com.krava.dagger2test.presentation.model;

import org.json.JSONObject;

/**
 * Created by krava2008 on 13.10.16.
 */

public class WindWeatherObject {
    private double speed = 0;
    private double deg = 0;

    public WindWeatherObject(){

    }

    public WindWeatherObject(JSONObject json){
        parse(json);
    }

    private void parse(JSONObject json){
        speed = json.optDouble("speed", speed);
        deg = json.optDouble("deg", deg);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }
}
