package com.enjot.materialweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeatherEntity::class],
    version = 1
)
abstract class WeatherDatabase: RoomDatabase() {

    abstract val dao: WeatherDao
}