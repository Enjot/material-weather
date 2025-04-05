package com.enjot.materialweather.di

import com.enjot.materialweather.data.remote.openweathermap.HttpClientFactory
import org.koin.dsl.module


val networkModule = module {
    single {
        HttpClientFactory.build()
    }
}