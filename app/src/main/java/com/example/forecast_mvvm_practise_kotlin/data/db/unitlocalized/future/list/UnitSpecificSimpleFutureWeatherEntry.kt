package com.example.forecast_mvvm_practise_kotlin.data.db.unitlocalized.future.list

import org.threeten.bp.LocalDate

interface UnitSpecificSimpleFutureWeatherEntry {
    val date:LocalDate
    val avgTemperature:Double
    val conditionText:String
    val conditionIconUrl:String
}