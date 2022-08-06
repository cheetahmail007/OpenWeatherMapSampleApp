package com.example.weatherapiusingcoroutines.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapiusingcoroutines.model.remote.Repository
import com.example.weatherapiusingcoroutines.model.remote.data.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: Repository) : ViewModel() {
    val weatherLiveData = MutableLiveData<WeatherResponse>()
    val error = MutableLiveData<String>()
    val processing = MutableLiveData<Boolean>()

    fun loadWeather() {
        viewModelScope.launch(Dispatchers.IO)
        {
            try {
                processing.postValue(true)
                val response = repository.getRemoteData()
                processing.postValue(false)
                if (!response.isSuccessful) {
                    error.postValue("Failed to load data.Error code: ${response.code()}")
                    return@launch
                }
                response.body()?.let {
                    weatherLiveData.postValue(it)
                }
            } catch (e: Exception) {
                error.postValue("Error is : $e")
                e.printStackTrace()
                processing.postValue(false)
            }
        }
    }
}