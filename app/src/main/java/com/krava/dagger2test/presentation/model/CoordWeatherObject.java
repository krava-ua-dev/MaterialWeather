package com.krava.dagger2test.presentation.model;

import org.json.JSONObject;

/**
 * Created by krava2008 on 13.10.16.
 */

public class CoordWeatherObject {
    /**
     * longitude
     */
    private double lon  = 0;
    /**
     * latitude
     */
    private double lat  = 0;

    public CoordWeatherObject(){

    }

    public CoordWeatherObject(JSONObject json){
        parse(json);
    }

    private void parse(JSONObject json){
        if(json != null) {
            lon = json.optDouble("lon", lon);
            lat = json.optDouble("lat", lat);
        }
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
