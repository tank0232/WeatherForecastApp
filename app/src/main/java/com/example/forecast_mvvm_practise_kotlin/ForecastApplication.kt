package com.example.forecast_mvvm_practise_kotlin

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.example.forecast_mvvm_practise_kotlin.data.db.ForecastDatabase
import com.example.forecast_mvvm_practise_kotlin.data.network.*
import com.example.forecast_mvvm_practise_kotlin.data.provider.*
import com.example.forecast_mvvm_practise_kotlin.data.respository.ForecastRespository
import com.example.forecast_mvvm_practise_kotlin.data.respository.ForecastRespositoryImpl
import com.example.forecast_mvvm_practise_kotlin.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.forecast_mvvm_practise_kotlin.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.example.forecast_mvvm_practise_kotlin.ui.weather.future.list.FutureListWeatherViewModel
import com.example.forecast_mvvm_practise_kotlin.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy{
        import(androidXModule(this@ForecastApplication))
        bind() from singleton {ForecastDatabase(instance()) }
        bind() from singleton {instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton {instance<ForecastDatabase>().futureWeatherDao() }
        bind() from singleton {instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton {WeatherNetworkDataSourceImpl(instance())}
        bind<LocationProvider>() with singleton {LocationProviderImpl(instance(),instance())}
        bind<ForecastRespository>() with singleton { ForecastRespositoryImpl(instance(),instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind () from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
        bind() from factory {detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate, instance(),instance())}
    }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false)
    }
}