package com.example.forecast_mvvm_practise_kotlin.data.network.response

import com.example.forecast_mvvm_practise_kotlin.data.db.entity.CurrentWeatherEntry
import com.example.forecast_mvvm_practise_kotlin.data.db.entity.Location
import com.example.forecast_mvvm_practise_kotlin.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation

)