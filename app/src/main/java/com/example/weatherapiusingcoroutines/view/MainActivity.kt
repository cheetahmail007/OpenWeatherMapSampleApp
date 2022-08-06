package com.example.weatherapiusingcoroutines.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapiusingcoroutines.databinding.ActivityMainBinding
import com.example.weatherapiusingcoroutines.model.remote.ApiService
import com.example.weatherapiusingcoroutines.model.remote.Repository
import com.example.weatherapiusingcoroutines.model.remote.data.WeatherResponse
import com.example.weatherapiusingcoroutines.viewmodel.WeatherViewModel
import com.example.weatherapiusingcoroutines.viewmodel.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var weatherViewModelFactory: WeatherViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherViewModelFactory = WeatherViewModelFactory(Repository(ApiService.getInstance()))
        weatherViewModel =
            ViewModelProvider(this, weatherViewModelFactory)[WeatherViewModel::class.java]

        weatherViewModel.loadWeather()

        setUpObservers()
    }

    private fun setUpObservers() {
        weatherViewModel.weatherLiveData.observe(this) {
            setResult(it)
        }

        weatherViewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        weatherViewModel.processing.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setResult(weatherResponse: WeatherResponse) {
        weatherResponse.apply {
            binding.apply {
                tvMain.text = "Main: ${weather[0].main}"
                tvDescription.text = "Description: ${weather[0].description}"
                tvFeelsLike.text = "FeelsLike: ${main.feels_like}"
                tvLat.text = "Lat: ${coord.lat}"
                tvLon.text = "Lon: ${coord.lon}"
                tvTemp.text = "Temp: ${main.temp}"
            }
        }
    }
}