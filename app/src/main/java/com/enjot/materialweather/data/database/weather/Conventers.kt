package com.enjot.materialweather.data.database.weather

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()
    
    @TypeConverter
    fun fromHourlyList(value: List<WeatherEntity.HourlyWeather>?): String = gson.toJson(value)
    
    @TypeConverter
    fun toHourlyList(value: String): List<WeatherEntity.HourlyWeather>? =
        gson.fromJson(value, object : TypeToken<List<WeatherEntity.HourlyWeather>>() {}.type)
    
    @TypeConverter
    fun fromDailyList(value: List<WeatherEntity.DailyWeather>?): String = gson.toJson(value)
    
    @TypeConverter
    fun toDailyList(value: String): List<WeatherEntity.DailyWeather>? =
        gson.fromJson(value, object : TypeToken<List<WeatherEntity.DailyWeather>>() {}.type)
}
