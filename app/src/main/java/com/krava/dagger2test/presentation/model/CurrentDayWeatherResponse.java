package com.krava.dagger2test.presentation.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krava2008 on 13.10.16.
 */

public class CurrentDayWeatherResponse {
    private int id = 0;
    private CoordWeatherObject coord;
    private String base = "";
    private WindWeatherObject wind;
    private List<WeatherWeatherObject> weathers;
    private long date = 0;
    private String name = "";
    private int rain = 0;
    private int clouds = 0;
    private MainWeatherObject main;
    private String dateText = "";
    private SysWeatherObject sys;

    public CurrentDayWeatherResponse(){

    }

    public CurrentDayWeatherResponse(JSONObject json){
        parse(json);
    }

    private void parse(JSONObject json){
        coord = new CoordWeatherObject(json.optJSONObject("coord"));
        base = json.optString("base", base);
        wind = new WindWeatherObject(json.optJSONObject("wind"));
        date = json.optLong("dt", date);
        id = json.optInt("id", id);
        name = json.optString("name", name);
        main = new MainWeatherObject(json.optJSONObject("main"));
        if(json.has("rain")){
            rain = json.optJSONObject("rain").optInt("3h", rain);
        }
        if(json.has("clouds")){
            clouds = json.optJSONObject("clouds").optInt("all", clouds);
        }
        dateText = json.optString("dt_text", dateText);
        if(json.has("weather")){
            weathers = new ArrayList<>();
            JSONArray jsonArray = json.optJSONArray("weather");
            int lenght = jsonArray.length();
            for(int i = 0; i < lenght; i++){
                weathers.add(new WeatherWeatherObject(jsonArray.optJSONObject(i)));
            }
        }
        if(json.has("sys")){
            sys = new SysWeatherObject(json.optJSONObject("sys"));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CoordWeatherObject getCoord() {
        return coord;
    }

    public void setCoord(CoordWeatherObject coord) {
        this.coord = coord;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public WindWeatherObject getWind() {
        return wind;
    }

    public void setWind(WindWeatherObject wind) {
        this.wind = wind;
    }

    public List<WeatherWeatherObject> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<WeatherWeatherObject> weathers) {
        this.weathers = weathers;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRain() {
        return rain;
    }

    public void setRain(int rain) {
        this.rain = rain;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public MainWeatherObject getMain() {
        return main;
    }

    public void setMain(MainWeatherObject main) {
        this.main = main;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public SysWeatherObject getSys() {
        return sys;
    }

    public void setSys(SysWeatherObject sys) {
        this.sys = sys;
    }
}
