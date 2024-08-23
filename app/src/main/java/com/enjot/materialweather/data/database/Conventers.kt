package com.enjot.materialweather.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromHourlyList(value: List<WeatherEntity.HourlyWeather>?): String = Json.encodeToString(value)
    
    @TypeConverter
    fun toHourlyList(value: String): List<WeatherEntity.HourlyWeather> =
        Json.decodeFromString<List<WeatherEntity.HourlyWeather>>(value)
    
    @TypeConverter
    fun fromDailyList(value: List<WeatherEntity.DailyWeather>?): String = Json.encodeToString(value)
    
    @TypeConverter
    fun toDailyList(value: String): List<WeatherEntity.DailyWeather> =
        Json.decodeFromString<List<WeatherEntity.DailyWeather>>(value)
}