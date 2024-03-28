package com.enjot.materialweather.domain.repository

import com.enjot.materialweather.domain.model.SavedLocation

interface SavedLocationsRepository {
    
    suspend fun getSavedLocations(): List<SavedLocation>
    suspend fun addToSavedLocations(savedLocation: SavedLocation)
    suspend fun deleteFromSavedLocations(savedLocation: SavedLocation)
}