package com.enjot.materialweather.domain.model

import java.time.LocalTime

data class HourlyWeather(
    val localTime: LocalTime,
    val temp: Int,
    val humidity: Int,
    val windSpeed: Double,
    val windDeg: Int,
    val pop: Double,
    val rainPrecipitation: Double?,
    val snowPrecipitation: Double?,
    val icon: String
)
