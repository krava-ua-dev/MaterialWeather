package com.krava.dagger2test.presentation.model;

import org.json.JSONObject;

/**
 * Created by krava2008 on 13.10.16.
 */

public class WeatherWeatherObject {
    private int id = 0;
    private String main = "";
    private String description = "";
    private String icon  = "";

    public WeatherWeatherObject(){

    }

    public WeatherWeatherObject(JSONObject json){
        parse(json);
    }

    private void parse(JSONObject json){
        id = json.optInt("id", id);
        main = json.optString("main", main);
        description = json.optString("description", description);
        icon = json.optString("icon", icon);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
