package com.krava.dagger2test.presentation.model;

import org.json.JSONObject;

/**
 * Created by krava2008 on 27.10.16.
 */

public class CityWeatherObject {
    private int id;
    private String name;
    private CoordWeatherObject coord;
    private String country;

    public CityWeatherObject(JSONObject jsonObject) {
        parse(jsonObject);
    }

    private void parse(JSONObject jsonObject) {
        id = jsonObject.optInt("id");
        name = jsonObject.optString("name");
        coord = new CoordWeatherObject(jsonObject.optJSONObject("coord"));
        country = jsonObject.optString("country");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CoordWeatherObject getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }
}
