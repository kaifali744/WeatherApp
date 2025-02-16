package com.example.weatherapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository : WeatherRepository) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData : LiveData<WeatherResponse?> get() = _weatherData

    fun fetchWeather(city:String, apiKey:String){
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city ,apiKey)
                if (response.isNotEmpty()){
                    _weatherData.postValue(response[0])
                }else {
                    Log.e("API_RESPONSE", "Empty response")
                }
            }catch (e : Exception){
                Log.e("API_ERROR", "Error fetching data: ${e.message}")
            }
        }
    }
}