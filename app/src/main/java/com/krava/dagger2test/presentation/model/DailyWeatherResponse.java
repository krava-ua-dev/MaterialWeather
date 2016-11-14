package com.krava.dagger2test.presentation.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krava2008 on 27.10.16.
 */

public class DailyWeatherResponse {
    private int cod;
    private double message;
    private CityWeatherObject city;
    private int cnt;
    private List<DailyWeatherObject> list;

    public DailyWeatherResponse(JSONObject jsonObject) {
        parse(jsonObject);
    }

    private void parse(JSONObject jsonObject) {
        cod = jsonObject.optInt("cod");
        message = jsonObject.optDouble("message");
        city = new CityWeatherObject(jsonObject.optJSONObject("city"));
        cnt = jsonObject.optInt("cnt");
        list = new ArrayList<>();
        if(jsonObject.has("list")){
            JSONArray listJson = jsonObject.optJSONArray("list");
            int size = listJson.length();
            for(int i = 0; i < size; i++) {
                list.add(new DailyWeatherObject(listJson.optJSONObject(i)));
            }
        }
    }

    public int getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public CityWeatherObject getCity() {
        return city;
    }

    public int getCnt() {
        return cnt;
    }

    public List<DailyWeatherObject> getList() {
        return list;
    }
}

