package com.example.weatherapiusingcoroutines.model.remote

import com.example.weatherapiusingcoroutines.model.remote.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    val retrofit: Retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            connectTimeout(50, TimeUnit.SECONDS)
            readTimeout(50, TimeUnit.SECONDS)
            writeTimeout(50, TimeUnit.SECONDS)
        }.build()

        val retrofit = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)

        }.build()
        retrofit
    }
}
