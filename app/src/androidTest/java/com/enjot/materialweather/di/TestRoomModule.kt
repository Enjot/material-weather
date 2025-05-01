package com.enjot.materialweather.di

import androidx.room.Room
import com.enjot.materialweather.weather.data.database.WeatherDatabase
import org.koin.dsl.module


val testDatabaseModule = module {
    single {
        Room.inMemoryDatabaseBuilder(get(), WeatherDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    single { get<WeatherDatabase>().weatherDao() }
    single { get<WeatherDatabase>().savedLocationDao() }
}