package com.enjot.materialweather.fakes

import com.enjot.materialweather.weather.data.database.SavedLocationDao
import com.enjot.materialweather.weather.data.database.entity.SavedLocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SavedLocationDaoFake: SavedLocationDao {
    
    
    private val savedLocations = MutableStateFlow(mutableListOf<SavedLocationEntity>())
    
    override fun getSavedLocations(): Flow<List<SavedLocationEntity>> {
        return savedLocations
    }
    
    override suspend fun insertSavedLocation(savedLocation: SavedLocationEntity) {
        savedLocations.value.add(savedLocation)
    }
    
    override suspend fun deleteSavedLocation(savedLocation: SavedLocationEntity) {
        savedLocations.value.remove(savedLocation)
    }
}
