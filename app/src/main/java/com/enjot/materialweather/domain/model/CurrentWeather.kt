package com.enjot.materialweather.domain.model

import java.time.LocalDateTime
import java.time.LocalTime

data class CurrentWeather(
    val localDateTime: LocalDateTime,
    val sunrise: LocalTime,
    val sunset: LocalTime,
    val temp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val clouds: Int,
    val uvi: Double,
    val visibility: Int,
    val windSpeed: Double,
    val windGust: Double? = null,
    val windDeg: Int,
    val rainPrecipitation: Double? = null,
    val snowPrecipitation: Double? = null,
    val weather: String,
    val description: String,
    val icon: String
)