package com.example.forecast_mvvm_practise_kotlin.internal

import kotlinx.coroutines.*

fun <T> lazyDeferred(block: suspend CoroutineScope.()->T):Lazy<Deferred<T>>{
     return lazy {
         GlobalScope.async(
             start = CoroutineStart.LAZY
         ){
             block.invoke(this)
         }

     }
}