package com.example.weatherapiusingcoroutines.model.remote

data class RequestData(
    val lat: String,
    val lon: String,
    val appid: String
)
