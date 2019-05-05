package com.example.forecast_mvvm_practise_kotlin.data.network

import androidx.lifecycle.LiveData
import com.example.forecast_mvvm_practise_kotlin.data.network.response.CurrentWeatherResponse
import com.example.forecast_mvvm_practise_kotlin.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>


    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        languageCode: String
    )
}