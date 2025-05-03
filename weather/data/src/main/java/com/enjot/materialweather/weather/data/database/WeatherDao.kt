package com.enjot.materialweather.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enjot.materialweather.weather.data.database.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    
    @Query("SELECT * FROM weather_info WHERE id = 1")
    fun getWeather(): Flow<WeatherEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)
}
