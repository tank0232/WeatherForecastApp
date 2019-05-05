package com.example.forecast_mvvm_practise_kotlin.ui.weather.future.detail

import com.example.forecast_mvvm_practise_kotlin.data.provider.UnitProvider
import com.example.forecast_mvvm_practise_kotlin.data.respository.ForecastRespository
import com.example.forecast_mvvm_practise_kotlin.internal.lazyDeferred
import com.example.forecast_mvvm_practise_kotlin.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRespository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
    }
}
