package com.example.forecast_mvvm_practise_kotlin.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecast_mvvm_practise_kotlin.data.provider.UnitProvider
import com.example.forecast_mvvm_practise_kotlin.data.respository.ForecastRespository

class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRespository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository,unitProvider) as T
    }
}