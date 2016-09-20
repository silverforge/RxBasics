package xyz.sevil.rx.rxbasics;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.sevil.rx.rxbasics.model.WeatherInfo;

public class WeatherRepository {

    private OpenWeatherAPI openWeatherAPI;

    public WeatherRepository() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    HttpUrl url = chain.request().url().newBuilder()
                            .addEncodedQueryParameter("units", "metric")
                            .addEncodedQueryParameter("APPID", "85ce54fbbc886bee15f72e2e0e8b5a3b")
                            .build();

                    Request request = chain.request().newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .build();

        openWeatherAPI = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(OpenWeatherAPI.class);
    }

    public WeatherInfo getCityWeather(String cityname) {
        WeatherInfo retValue = new WeatherInfo();

        try {
            Call<WeatherInfo> weatherOfCity = openWeatherAPI.getWeatherOfCity(cityname);
            retValue = weatherOfCity.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retValue;
    }
}