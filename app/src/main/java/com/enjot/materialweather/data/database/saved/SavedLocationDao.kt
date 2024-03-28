package com.enjot.materialweather.data.database.saved

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedLocationDao {
    
    @Query("SELECT * FROM saved_location")
    suspend fun getSavedLocations(): List<SavedLocationEntity>
    
    @Insert
    suspend fun insertSavedLocation(savedLocation: SavedLocationEntity)
    
    @Delete
    suspend fun deleteSavedLocation(savedLocation: SavedLocationEntity)
    
}