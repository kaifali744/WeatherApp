package com.example.weatherapp.retrofit

import com.example.weatherapp.model.WeatherResponse
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey : String,
        @Query("units")units : String = "metrics"
    ): List<WeatherResponse>
}