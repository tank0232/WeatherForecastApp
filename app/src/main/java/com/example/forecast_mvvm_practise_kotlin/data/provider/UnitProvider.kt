package com.example.forecast_mvvm_practise_kotlin.data.provider

import com.example.forecast_mvvm_practise_kotlin.internal.UnitSystem


interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}