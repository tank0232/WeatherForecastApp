package com.example.forecast_mvvm_practise_kotlin.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecast_mvvm_practise_kotlin.data.provider.UnitProvider
import com.example.forecast_mvvm_practise_kotlin.data.respository.ForecastRespository
import com.example.forecast_mvvm_practise_kotlin.internal.UnitSystem
import com.example.forecast_mvvm_practise_kotlin.internal.lazyDeferred
import com.example.forecast_mvvm_practise_kotlin.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRespository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository,unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }



}
