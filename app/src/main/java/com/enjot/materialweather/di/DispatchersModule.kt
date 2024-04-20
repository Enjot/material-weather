package com.enjot.materialweather.di

import com.enjot.materialweather.data.util.StandardDispatchers
import com.enjot.materialweather.domain.repository.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    
    @Provides
    @Singleton
    fun provideStandardDispatchers(): DispatcherProvider = StandardDispatchers()
}