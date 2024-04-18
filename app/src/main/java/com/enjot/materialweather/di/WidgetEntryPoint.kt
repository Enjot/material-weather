package com.enjot.materialweather.di

import com.enjot.materialweather.domain.usecase.weather.GetLocalWeatherUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    fun getLocalWeatherUseCase(): GetLocalWeatherUseCase
}
