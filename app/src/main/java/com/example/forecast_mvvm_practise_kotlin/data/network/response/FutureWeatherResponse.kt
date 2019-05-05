package com.example.forecast_mvvm_practise_kotlin.data.network.response

import com.example.forecast_mvvm_practise_kotlin.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class FutureWeatherResponse(
    @SerializedName("forecast")
    val futureWeatherEntries: ForecastDaysContainer,
    val location: WeatherLocation
)