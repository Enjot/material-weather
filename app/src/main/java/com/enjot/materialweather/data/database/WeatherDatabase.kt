package com.enjot.materialweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [WeatherEntity::class, SavedLocationEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
    
    abstract fun savedLocationDao(): SavedLocationDao
}
