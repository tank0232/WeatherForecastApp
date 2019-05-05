package com.example.forecast_mvvm_practise_kotlin.data.network.response

import com.example.forecast_mvvm_practise_kotlin.data.db.entity.FutureWeatherEntry
import com.google.gson.annotations.SerializedName

data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)
