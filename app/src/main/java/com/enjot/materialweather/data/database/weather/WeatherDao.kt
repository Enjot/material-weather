package com.enjot.materialweather.data.database.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    
    @Query("SELECT * FROM weather_info WHERE id = 1")
    fun getWeather(): Flow<WeatherEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: WeatherEntity)
}
