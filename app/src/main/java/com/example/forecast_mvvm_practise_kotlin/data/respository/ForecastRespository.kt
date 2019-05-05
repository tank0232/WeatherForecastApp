package com.example.forecast_mvvm_practise_kotlin.data.respository

import androidx.lifecycle.LiveData
import com.example.forecast_mvvm_practise_kotlin.data.db.entity.WeatherLocation
import com.example.forecast_mvvm_practise_kotlin.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.forecast_mvvm_practise_kotlin.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.example.forecast_mvvm_practise_kotlin.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate


interface ForecastRespository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>

    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>

    suspend fun getFutureWeatherByDate(date: LocalDate, metric:Boolean): LiveData<out UnitSpecificDetailFutureWeatherEntry>
}