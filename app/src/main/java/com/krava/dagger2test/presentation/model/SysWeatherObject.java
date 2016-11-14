package com.krava.dagger2test.presentation.model;

import org.json.JSONObject;

/**
 * Created by krava2008 on 13.10.16.
 */

public class SysWeatherObject {
    private int type = 0;
    private int id = 0;
    private double message = 0;
    private String country = "";
    private long sunrise = 0;
    private long sunset = 0;

    public SysWeatherObject(){

    }

    public SysWeatherObject(JSONObject json){
        parse(json);
    }

    private void parse(JSONObject json){
        type = json.optInt("type", type);
        id = json.optInt("id", id);
        message = json.optDouble("message", message);
        country = json.optString("country", country);
        sunrise = json.optLong("sunrise", sunrise);
        sunset = json.optLong("sunset", sunset);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
