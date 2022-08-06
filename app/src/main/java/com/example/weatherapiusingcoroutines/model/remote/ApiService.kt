package com.example.weatherapiusingcoroutines.model.remote

import com.example.weatherapiusingcoroutines.model.remote.data.WeatherResponse
import com.example.weatherapiusingcoroutines.model.remote.Constants.MULTIPLE_WEATHER_END_POINT
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(MULTIPLE_WEATHER_END_POINT)
    suspend fun getWeather(): Response<WeatherResponse>

    companion object {
        fun getInstance(): ApiService = ApiClient.retrofit.create(ApiService::class.java)
    }
}
