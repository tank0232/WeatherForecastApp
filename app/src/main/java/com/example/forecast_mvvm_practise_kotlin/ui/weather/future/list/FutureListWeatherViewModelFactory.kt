package com.example.forecast_mvvm_practise_kotlin.ui.weather.future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecast_mvvm_practise_kotlin.data.provider.UnitProvider
import com.example.forecast_mvvm_practise_kotlin.data.respository.ForecastRespository

class FutureListWeatherViewModelFactory(
    private val forecastRespository: ForecastRespository,
    private val unitProvider: UnitProvider

):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(
            forecastRespository,
            unitProvider

        )as T
    }

}