package com.krava.dagger2test.presentation.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krava2008 on 27.10.16.
 */

public class DailyWeatherObject {
    private long date;
    private double pressure;
    private int humidity;
    private List<WeatherWeatherObject> weather;
    private TempWeatherObject temp;


    public DailyWeatherObject(JSONObject jsonObject){
        parse(jsonObject);
    }

    private void parse(JSONObject jsonObject){
        date = jsonObject.optInt("dt");
        pressure = jsonObject.optDouble("pressure");
        humidity = jsonObject.optInt("humidity");
        weather = new ArrayList<>();
        if(jsonObject.has("weather")) {
            JSONArray weahterJson = jsonObject.optJSONArray("weather");
            int size = weahterJson.length();
            for(int i = 0; i < size; i++) {
                weather.add(new WeatherWeatherObject(weahterJson.optJSONObject(i)));
            }
        }
        temp = new TempWeatherObject(jsonObject.optJSONObject("temp"));
    }

    public long getDate() {
        return date;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public List<WeatherWeatherObject> getWeather() {
        return weather;
    }

    public TempWeatherObject getTemp() {
        return temp;
    }
}
