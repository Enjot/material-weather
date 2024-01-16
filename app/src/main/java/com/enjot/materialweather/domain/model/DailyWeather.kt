package com.enjot.materialweather.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class DailyWeather(
    val localDate: LocalDate,
    val sunrise: LocalTime,
    val sunset: LocalTime,
    val moonrise: LocalTime,
    val moonset: LocalTime,
    val moonPhase: Double,
    val summary: String,
    val tempDay: Int,
    val tempNight: Int,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
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
