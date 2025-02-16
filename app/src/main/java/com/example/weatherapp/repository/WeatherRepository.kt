package com.example.weatherapp.repository

import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.retrofit.RetrofitInstance

class WeatherRepository {
    suspend fun getWeather(city : String, apiKey : String): List<WeatherResponse>{
        return RetrofitInstance.api.getWeather(city,apiKey)
    }
}