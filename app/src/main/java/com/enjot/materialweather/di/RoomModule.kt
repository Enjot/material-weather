package com.enjot.materialweather.di

import androidx.room.Room
import com.enjot.materialweather.data.database.WeatherDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val databaseModule = module {
    single {
        Room
            .databaseBuilder(
                androidContext(),
                WeatherDatabase::class.java,
                "weather_db"
            )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single {
        get<WeatherDatabase>().weatherDao()
    }

    single {
        get<WeatherDatabase>().savedLocationDao()
    }
}