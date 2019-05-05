package com.example.forecast_mvvm_practise_kotlin.data.db.unitlocalized.current

interface UnitSpecificCurrentWeatherEntry {
    val temperature: Double
    val conditionText: String
    val conditionIconUrl:String
    val windSpeed:Double
    val windDirection: String
    val precipitationVolume:Double
    val feelsLikeTemperature:Double
    val visibilityDistance: Double
}