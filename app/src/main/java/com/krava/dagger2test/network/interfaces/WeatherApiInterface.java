package com.krava.dagger2test.network.interfaces;



import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by krava2008 on 30.10.16.
 */

public interface WeatherApiInterface {

    @GET("weather")
    Observable<Response<ResponseBody>> getForDayByCityName(@Query("q") String cityName, @Query("APPID") String apiKey);

    @GET("weather")
    Observable<Response<ResponseBody>> getForDayByCityLocation(@Query("lat") String latitude, @Query("lon") String longitide, @Query("APPID") String apiKey);

    @GET("forecast")
    Observable<Response<ResponseBody>> forecast(@Query("q") String cityName, @Query("APPID") String apiKey);

    @GET("forecast/daily")
    Observable<Response<ResponseBody>> daily(@Query("q") String cityName, @Query("cnt") int days, @Query("APPID") String apiKey);
}
