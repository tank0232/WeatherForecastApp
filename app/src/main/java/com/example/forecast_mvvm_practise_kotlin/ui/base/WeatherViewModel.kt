package com.example.forecast_mvvm_practise_kotlin.ui.base

import androidx.lifecycle.ViewModel
import com.example.forecast_mvvm_practise_kotlin.data.provider.UnitProvider
import com.example.forecast_mvvm_practise_kotlin.data.respository.ForecastRespository
import com.example.forecast_mvvm_practise_kotlin.internal.UnitSystem
import com.example.forecast_mvvm_practise_kotlin.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRespository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}