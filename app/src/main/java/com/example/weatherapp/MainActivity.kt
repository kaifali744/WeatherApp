package com.example.weatherapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewModel.WeatherViewModel
import com.example.weatherapp.viewModel.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewModel()
        binding.viewModel = weatherViewModel
        binding.lifecycleOwner = this

        binding.searchIcon.setOnClickListener {
            val city = binding.etCity.text.toString().trim()
            if (city.isNotEmpty()){
                weatherViewModel.fetchWeather(city,R.string.apiKey.toString())
            }
        }
        weatherViewModel.weatherData.observe(this){weather ->
            weather?.let {
                val iconUrl ="https://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png"
                binding.weatherIcon.visibility = View.VISIBLE
                Glide.with(this)
                    .load(iconUrl)
                    .into(binding.weatherIcon)
            }
        }

    }

    private fun setupViewModel() {
        val weatherRepository = WeatherRepository()
        val weatherViewModelFactory = WeatherViewModelFactory(weatherRepository)
        weatherViewModel = ViewModelProvider(this, weatherViewModelFactory)[WeatherViewModel::class.java]
    }
}