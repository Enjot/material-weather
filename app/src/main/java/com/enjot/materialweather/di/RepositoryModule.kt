package com.enjot.materialweather.di

import com.enjot.materialweather.data.repository.LocalRepositoryImpl
import com.enjot.materialweather.data.repository.RemoteRepositoryImpl
import com.enjot.materialweather.domain.repository.LocalRepository
import com.enjot.materialweather.domain.repository.RemoteRepository
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
        weatherRepoImpl: RemoteRepositoryImpl
    ): RemoteRepository
    
    @Binds
    @Singleton
    abstract fun bindSavedLocationsRepository(
        savedLocationsRepoImpl: LocalRepositoryImpl
    ): LocalRepository
}