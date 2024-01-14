package com.enjot.materialweather.di

import com.enjot.materialweather.data.location.DefaultLocationTracker
import com.enjot.materialweather.data.repository.WeatherRepositoryImpl
import com.enjot.materialweather.domain.location.LocationTracker
import com.enjot.materialweather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}