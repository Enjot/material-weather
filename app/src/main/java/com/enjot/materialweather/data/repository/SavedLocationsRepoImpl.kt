package com.enjot.materialweather.data.repository

import com.enjot.materialweather.data.database.saved.SavedLocationDao
import com.enjot.materialweather.data.mapper.toSavedLocation
import com.enjot.materialweather.data.mapper.toSavedLocationEntity
import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.repository.SavedLocationsRepository
import javax.inject.Inject

class SavedLocationsRepoImpl @Inject constructor(
    private val dao: SavedLocationDao
): SavedLocationsRepository {
    
    override suspend fun getSavedLocations(): List<SavedLocation> {
        return dao.getSavedLocations().map { it.toSavedLocation() }
    }
    
    override suspend fun addToSavedLocations(savedLocation: SavedLocation) {
        dao.insertSavedLocation(savedLocation.toSavedLocationEntity())
    }
    
    override suspend fun deleteFromSavedLocations(savedLocation: SavedLocation) {
        dao.deleteSavedLocation(savedLocation.toSavedLocationEntity())
    }
}