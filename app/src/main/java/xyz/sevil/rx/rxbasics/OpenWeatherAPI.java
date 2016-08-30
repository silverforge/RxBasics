package xyz.sevil.rx.rxbasics;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.sevil.rx.rxbasics.model.WeatherInfo;

public interface OpenWeatherAPI {

    @GET("weather")
    Call<WeatherInfo> getWeatherOfCity(@Query("q") String cityName);
}
