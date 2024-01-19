package com.enjot.materialweather.domain.model

import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.roundToInt

data class CurrentWeather(
    val localDateTime: LocalDateTime,
    val sunrise: LocalTime,
    val sunset: LocalTime,
    val temp: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val feelsLike: Int,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val clouds: Int,
    val uvi: Double,
    val windSpeed: Double,
    val windGust: Double? = null,
    val windDeg: Int,
    val rainPrecipitation: Double? = null,
    val snowPrecipitation: Double? = null,
    val weather: String,
    val description: String,
    val icon: String,
    val conditions: WeatherConditions = WeatherConditions(
        sunrise,
        sunset,
        windSpeed.roundToInt(),
        windDeg,
        humidity,
        dewPoint.roundToInt(),
        pressure,
        uvi.roundToInt()
    )
)

data class WeatherConditions(
    val sunrise: LocalTime,
    val sunset: LocalTime,
    val windSpeed: Int,
    val windDeg: Int,
    val humidity: Int,
    val dewPoint: Int,
    val pressure: Int,
    val uvi: Int
)