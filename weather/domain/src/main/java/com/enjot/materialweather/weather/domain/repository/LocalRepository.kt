package com.enjot.materialweather.weather.domain.repository

import com.enjot.materialweather.weather.domain.model.SavedLocation
import com.enjot.materialweather.weather.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    
    fun getLocalWeather(): Flow<WeatherInfo>
    fun getSavedLocations(): Flow<List<SavedLocation>>
    suspend fun saveLocalWeather(weatherInfo: WeatherInfo)
    suspend fun addSavedLocation(savedLocation: SavedLocation)
    suspend fun deleteSavedLocation(savedLocation: SavedLocation)
    
}