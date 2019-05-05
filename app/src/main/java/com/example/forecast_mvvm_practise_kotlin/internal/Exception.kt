package com.example.forecast_mvvm_practise_kotlin.internal

import java.io.IOException
import java.lang.Exception

class NoConnectivityException: IOException()
class  LocationPermissionNotGrantedException: Exception()
class DateNotFoundException:Exception()