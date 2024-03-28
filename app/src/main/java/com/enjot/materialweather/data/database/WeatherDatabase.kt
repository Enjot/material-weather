package com.enjot.materialweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enjot.materialweather.data.database.saved.SavedLocationDao
import com.enjot.materialweather.data.database.saved.SavedLocationEntity
import com.enjot.materialweather.data.database.weather.Converters
import com.enjot.materialweather.data.database.weather.WeatherDao
import com.enjot.materialweather.data.database.weather.WeatherEntity

@Database(
    entities = [WeatherEntity::class, SavedLocationEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase: RoomDatabase() {

    abstract val weatherDao: WeatherDao
    
    abstract val savedLocationDao: SavedLocationDao
}