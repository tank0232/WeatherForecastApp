package com.example.forecast_mvvm_practise_kotlin.ui.weather.future.list

import com.example.forecast_mvvm_practise_kotlin.data.provider.UnitProvider
import com.example.forecast_mvvm_practise_kotlin.data.respository.ForecastRespository
import com.example.forecast_mvvm_practise_kotlin.internal.lazyDeferred
import com.example.forecast_mvvm_practise_kotlin.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate


class FutureListWeatherViewModel(
    private  val forecastRespository: ForecastRespository,
    private  val unitProvider: UnitProvider

):WeatherViewModel(forecastRespository, unitProvider){

    val weatherEntries by lazyDeferred {
        forecastRespository.getFutureWeatherList(LocalDate.now(),super.isMetricUnit)

    }
}