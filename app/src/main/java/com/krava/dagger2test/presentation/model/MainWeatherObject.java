package com.krava.dagger2test.presentation.model;

import org.json.JSONObject;

/**
 * Created by krava2008 on 13.10.16.
 */

public class MainWeatherObject {
    private double temp = 0;
    private double tempMin = 0;
    private double tempMax = 0;
    private int pressure = 0;
    private int humidity = 0;

    public MainWeatherObject(){

    }

    public MainWeatherObject(JSONObject json){
        parse(json);
    }

    private void parse(JSONObject json){
        temp = json.optDouble("temp", temp);
        pressure = json.optInt("pressure", pressure);
        humidity = json.optInt("humidity", humidity);
        tempMax = json.optDouble("temp_max", tempMax);
        tempMin = json.optDouble("temp_min", tempMin);
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
