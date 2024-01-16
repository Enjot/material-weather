package com.enjot.materialweather.di

import com.enjot.materialweather.data.location.DefaultLocationProvider
import com.enjot.materialweather.domain.location.LocationProvider
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
    abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationProvider): LocationProvider
}