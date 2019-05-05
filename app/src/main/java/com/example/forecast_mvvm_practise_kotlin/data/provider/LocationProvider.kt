package com.example.forecast_mvvm_practise_kotlin.data.provider

import com.example.forecast_mvvm_practise_kotlin.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation):Boolean
    suspend fun getPreferredLocationString():String
}