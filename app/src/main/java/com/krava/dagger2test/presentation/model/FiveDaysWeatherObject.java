package com.krava.dagger2test.presentation.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krava2008 on 13.10.16.
 */

public class FiveDaysWeatherObject {
    private CoordWeatherObject coord;
    private String country;
    private int cod;
    private List<CurrentDayWeatherResponse> weathers;

    public FiveDaysWeatherObject(){

    }

    public FiveDaysWeatherObject(JSONObject json){
        parse(json);
    }

    private void parse(JSONObject json){
        coord = new CoordWeatherObject(json.optJSONObject("coord"));
        country = json.optString("country", country);
        cod = json.optInt("cod", cod);
        if(json.has("list")){
            weathers = new ArrayList<>();
            JSONArray jsonArray = json.optJSONArray("list");
            int length = jsonArray.length();

            for(int i = 0; i < length; i++){
                weathers.add(new CurrentDayWeatherResponse(jsonArray.optJSONObject(i)));
            }
        }
    }

    public CoordWeatherObject getCoord() {
        return coord;
    }

    public void setCoord(CoordWeatherObject coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public List<CurrentDayWeatherResponse> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<CurrentDayWeatherResponse> weathers) {
        this.weathers = weathers;
    }
}
