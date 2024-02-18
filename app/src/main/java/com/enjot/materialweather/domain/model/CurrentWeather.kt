package com.enjot.materialweather.domain.model

import java.time.LocalTime
import kotlin.math.roundToInt

data class CurrentWeather(
    val localFormattedTime: String,
    val temp: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val feelsLike: Int,
    val clouds: Int,
    val windGust: Double? = null,
    val rainPrecipitation: Double? = null,
    val snowPrecipitation: Double? = null,
    val weather: String,
    val description: String,
    val icon: String,
    val conditions: WeatherConditions
) {
    data class WeatherConditions(
        val sunrise: String,
        val sunset: String,
        val windSpeed: Int,
        val windDeg: Int,
        val humidity: Int,
        val dewPoint: Int,
        val pressure: Int,
        val uvi: Int
    )
}

