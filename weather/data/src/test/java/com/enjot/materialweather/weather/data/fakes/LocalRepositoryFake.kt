package com.enjot.materialweather.weather.data.fakes

import com.enjot.materialweather.weather.domain.model.SavedLocation
import com.enjot.materialweather.weather.domain.model.WeatherInfo
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LocalRepositoryFake : LocalRepository {
    
    var localWeather = MutableStateFlow(WeatherInfo())
    
    val savedLocations = MutableStateFlow(mutableListOf<SavedLocation>())
    
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
