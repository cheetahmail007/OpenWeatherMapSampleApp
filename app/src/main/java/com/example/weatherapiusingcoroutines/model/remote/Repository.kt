package com.example.weatherapiusingcoroutines.model.remote

class Repository (private val apiService: ApiService) {

    suspend fun getRemoteData()= apiService.getWeather()
}