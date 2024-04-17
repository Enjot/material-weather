package com.enjot.materialweather.data.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class WeatherDaoFake : WeatherDao {
    
    private var weatherEntity = MutableStateFlow(WeatherEntity())
    
    var isTableEmpty = false
    override fun getWeather(): Flow<WeatherEntity?> {
        return if (isTableEmpty) flow { emit(null) } else weatherEntity
    }
    
    override suspend fun insertWeather(weather: WeatherEntity) {
        weatherEntity.value = weather
    }
    
}