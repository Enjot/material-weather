package com.enjot.materialweather.di

import com.enjot.materialweather.data.repository.LocationRepositoryImpl
import com.enjot.materialweather.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun providesLocationTracker(
        defaultLocationTracker: LocationRepositoryImpl
    ): LocationRepository
}