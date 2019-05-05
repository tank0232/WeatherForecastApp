package com.example.forecast_mvvm_practise_kotlin.data.respository

import androidx.lifecycle.LiveData
import com.example.forecast_mvvm_practise_kotlin.data.db.CurrentWeatherDao
import com.example.forecast_mvvm_practise_kotlin.data.db.FutureWeatherDao
import com.example.forecast_mvvm_practise_kotlin.data.db.WeatherLocationDao
import com.example.forecast_mvvm_practise_kotlin.data.db.entity.WeatherLocation
import com.example.forecast_mvvm_practise_kotlin.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.forecast_mvvm_practise_kotlin.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.example.forecast_mvvm_practise_kotlin.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.example.forecast_mvvm_practise_kotlin.data.network.FORECAST_DAYS_COUNT
import com.example.forecast_mvvm_practise_kotlin.data.network.WeatherNetworkDataSource
import com.example.forecast_mvvm_practise_kotlin.data.network.response.CurrentWeatherResponse
import com.example.forecast_mvvm_practise_kotlin.data.network.response.FutureWeatherResponse
import com.example.forecast_mvvm_practise_kotlin.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*


class ForecastRespositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource:WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRespository {
    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
       return withContext(Dispatchers.IO){
           return@withContext weatherLocationDao.getLocation()
       }
     }

    init{
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever{newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
        }
            downloadedFutureWeather.observeForever { newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
       return withContext(Dispatchers.IO){
          initWeatherData()
           return@withContext if(metric) currentWeatherDao.getWeatherMetric()
           else currentWeatherDao.getWeatherImperial()
       }
    }

    override suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
       return withContext(Dispatchers.IO){
           initWeatherData()
           return@withContext if(metric)futureWeatherDao.getSimpleWeatherForecastsMetric(startDate)
           else futureWeatherDao.getSimpleWeatherForecastsImperial((startDate))
       }
    }
    override suspend fun getFutureWeatherByDate(
        date: LocalDate,
        metric: Boolean
    ): LiveData<out UnitSpecificDetailFutureWeatherEntry>{
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getDetailedWeatherByDateMetric(date)
            else futureWeatherDao.getDetailedWeatherByDateImperial(date)
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch (Dispatchers.IO  ){
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {

        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch ( Dispatchers.IO ){
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData(){

        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()

        if(lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }
        if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
        if(isFetchFutureNeeded())
            fetchFutureWeather()
    }

    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT

    }

    private suspend fun fetchFutureWeather() {
        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private suspend fun fetchCurrentWeather(){
       weatherNetworkDataSource.fetchCurrentWeather(
           locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
       )
    }
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime):Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)

    }
}