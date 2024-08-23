package com.enjot.materialweather.di

import com.enjot.materialweather.data.remote.openweathermap.HttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {

    @Singleton
    @Provides
    fun provideKtorClient(): HttpClient {
        return HttpClientFactory().build()
    }
}