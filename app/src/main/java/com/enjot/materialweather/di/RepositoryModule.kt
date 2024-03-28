package com.enjot.materialweather.di

import com.enjot.materialweather.data.repository.SavedLocationsRepoImpl
import com.enjot.materialweather.data.repository.WeatherRepoImpl
import com.enjot.materialweather.domain.repository.SavedLocationsRepository
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
    abstract fun bindWeatherRepository(
        weatherRepoImpl: WeatherRepoImpl
    ): WeatherRepository
    
    @Binds
    @Singleton
    abstract fun bindSavedLocationsRepository(
        savedLocationsRepoImpl: SavedLocationsRepoImpl
    ): SavedLocationsRepository
}