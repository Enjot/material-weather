package com.enjot.materialweather.weather

import androidx.room.Room
import com.enjot.materialweather.weather.data.database.WeatherDatabase
import com.enjot.materialweather.weather.data.remote.KtorRepository
import com.enjot.materialweather.weather.data.database.LocalRepositoryImpl
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import com.enjot.materialweather.weather.domain.repository.RemoteRepository
import com.enjot.materialweather.weather.domain.usecase.weather.GetWeatherFromLocationUseCase
import com.enjot.materialweather.weather.domain.usecase.weather.UpdateWeatherUseCase
import com.enjot.materialweather.weather.presentation.ui.screen.daily.DailyViewModel
import com.enjot.materialweather.weather.presentation.ui.screen.overview.OverviewViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherModule = module {
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

    viewModelOf(::OverviewViewModel)
    viewModelOf(::DailyViewModel)

    singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }
    singleOf(::KtorRepository) { bind<RemoteRepository>() }
}