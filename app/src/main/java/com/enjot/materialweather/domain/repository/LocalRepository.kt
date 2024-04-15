package com.enjot.materialweather.domain.repository

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    
    fun getLocalWeather(): Flow<WeatherInfo>
    suspend fun saveLocalWeather(weatherInfo: WeatherInfo?)
    fun getSavedLocations(): Flow<List<SavedLocation>>
    suspend fun addSavedLocation(savedLocation: SavedLocation)
    suspend fun deleteSavedLocation(savedLocation: SavedLocation)
    
}