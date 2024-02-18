package com.enjot.materialweather.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_info")
data class WeatherEntity(
    @PrimaryKey val id: Int = 1,
    @Embedded val place: SearchResult? = null,
    @Embedded val current: Current? = null,
    val hourly: List<HourlyWeather>? = null,
    val daily: List<DailyWeather>? = null,
    @Embedded val airPollution: AirPollution? = null
) {
    data class SearchResult(
        val city: String,
        val postCode: String? = null,
        val countryCode: String,
        @Embedded val coordinates: Coordinates
    ) {
        data class Coordinates(
            val lat: Double,
            val lon: Double
        )
    }
    data class Current(
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
        @Embedded val conditions: Conditions
    ) {
        data class Conditions(
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
    data class DailyWeather(
        val dayOfWeek: String,
        val sunrise: String,
        val sunset: String,
        val moonrise: String,
        val moonset: String,
        val moonPhase: Double,
        val summary: String,
        val tempDay: Int,
        val tempNight: Int,
        val pressure: Int,
        val humidity: Int,
        val dewPoint: Int,
        val uvi: Int,
        val clouds: Int,
        val windSpeed: Int,
        val windGust: Double?,
        val windDeg: Int,
        val pop: Double,
        val rainPrecipitation: Double?,
        val snowPrecipitation: Double?,
        val weather: String,
        val description: String,
        val icon: String
    )
    data class AirPollution(
        val aqi: Int,
        val nh3: Int,
        val no: Int,
        val co: Int,
        val no2: Int,
        val o3: Int,
        val pm10: Int,
        val pm25: Int,
        val so2: Int
    )
}
