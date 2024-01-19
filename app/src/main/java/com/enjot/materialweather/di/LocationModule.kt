package com.enjot.materialweather.di

import com.enjot.materialweather.data.location.DefaultLocationTracker
import com.enjot.materialweather.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun providesLocationTracker(
        defaultLocationTracker: DefaultLocationTracker
    ): LocationTracker
}