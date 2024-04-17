package com.enjot.materialweather.data.repository

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LocalRepositoryFake : LocalRepository {
    
    private var localWeather = MutableStateFlow(WeatherInfo())
    
    private val savedLocations = MutableStateFlow(mutableListOf<SavedLocation>())
    
    override fun getLocalWeather(): Flow<WeatherInfo> {
        return localWeather
    }
    
    override fun getSavedLocations(): Flow<List<SavedLocation>> {
        return savedLocations
    }
    
    override suspend fun saveLocalWeather(weatherInfo: WeatherInfo) {
        localWeather.value = weatherInfo
    }
    
    override suspend fun addSavedLocation(savedLocation: SavedLocation) {
        savedLocations.value.add(savedLocation)
    }
    
    override suspend fun deleteSavedLocation(savedLocation: SavedLocation) {
        savedLocations.value.remove(savedLocation)
    }
}
