package com.enjot.materialweather.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enjot.materialweather.weather.data.database.entity.SavedLocationEntity
import com.enjot.materialweather.weather.data.database.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class, SavedLocationEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    abstract fun savedLocationDao(): SavedLocationDao
}