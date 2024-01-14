package com.enjot.materialweather.domain.model

import java.time.LocalTime

data class HourlyWeather(
    val localTime: LocalTime,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windGust: Double?,
    val windDeg: Int,
    val pop: Double,
    val rainPrecipitation: Double?,
    val snowPrecipitation: Double?,
    val weather: String,
    val description: String,
    val icon: String
)
