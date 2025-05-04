package com.enjot.materialweather.weather.data.di

import androidx.room.Room
import com.enjot.materialweather.weather.data.database.LocalRepositoryImpl
import com.enjot.materialweather.weather.data.database.WeatherDatabase
import com.enjot.materialweather.weather.data.remote.KtorRepository
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import com.enjot.materialweather.weather.domain.repository.RemoteRepository
import com.enjot.materialweather.weather.domain.usecase.GetWeatherFromLocationUseCase
import com.enjot.materialweather.weather.domain.usecase.UpdateWeatherUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val weatherDataModule = module {
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

    single { get<WeatherDatabase>().weatherDao() }
    single { get<WeatherDatabase>().savedLocationDao() }

    singleOf(::UpdateWeatherUseCase)
    singleOf(::GetWeatherFromLocationUseCase)

    singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }
    singleOf(::KtorRepository) { bind<RemoteRepository>() }
}