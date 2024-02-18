package com.enjot.materialweather.domain.model

import java.time.LocalTime

data class HourlyWeather(
    val localFormattedTime: String,
    val temp: Int,
    val humidity: Int,
    val windSpeed: Int,
    val windDeg: Int,
    val pop: Double,
    val rainPrecipitation: Double?,
    val snowPrecipitation: Double?,
    val icon: String
)
