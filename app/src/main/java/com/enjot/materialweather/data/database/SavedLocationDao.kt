package com.enjot.materialweather.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedLocationDao {
    
    @Query("SELECT * FROM saved_location")
    fun getSavedLocations(): Flow<List<SavedLocationEntity>>
    
    @Insert
    suspend fun insertSavedLocation(savedLocation: SavedLocationEntity)
    
    @Delete
    suspend fun deleteSavedLocation(savedLocation: SavedLocationEntity)
    
}
