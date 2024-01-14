package com.enjot.materialweather.data.remote.openweathermap.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OneCallDto(
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("timezone") val timezone: String,
    @SerialName("timezone_offset") val timezoneOffset: Int,
    @SerialName("current") val current: Current,
    @SerialName("hourly") val hourly: List<Hourly>,
    @SerialName("daily") val daily: List<Daily>,
) {
    @Serializable
    data class Current(
        @SerialName("dt") val dt: Int,
        @SerialName("sunrise") val sunrise: Int,
        @SerialName("sunset") val sunset: Int,
        @SerialName("temp") val temp: Double,
        @SerialName("feels_like") val feelsLike: Double,
        @SerialName("pressure") val pressure: Int,
        @SerialName("humidity") val humidity: Int,
        @SerialName("dew_point") val dewPoint: Double,
        @SerialName("clouds") val clouds: Int,
        @SerialName("uvi") val uvi: Double,
        @SerialName("visibility") val visibility: Int,
        @SerialName("wind_speed") val windSpeed: Double,
        @SerialName("wind_gust") val windGust: Double? = null,
        @SerialName("wind_deg") val windDeg: Int,
        @SerialName("rain") val rain: Rain? = null,
        @SerialName("snow") val snow: Snow? = null,
        @SerialName("weather") val weather: List<Weather>
    ) {
        @Serializable
        data class Rain(
            @SerialName("1h") val precipitation: Double
        )
        
        @Serializable
        data class Snow(
            @SerialName("1h") val precipitation: Double
        )
        
        @Serializable
        data class Weather(
            @SerialName("id") val id: Int,
            @SerialName("main") val main: String,
            @SerialName("description") val description: String,
            @SerialName("icon") val icon: String
        )
    }
    
    @Serializable
    data class Hourly(
        @SerialName("dt") val dt: Int,
        @SerialName("temp") val temp: Double,
        @SerialName("feels_like") val feelsLike: Double,
        @SerialName("pressure") val pressure: Int,
        @SerialName("humidity") val humidity: Int,
        @SerialName("dew_point") val dewPoint: Double,
        @SerialName("uvi") val uvi: Double,
        @SerialName("clouds") val clouds: Int,
        @SerialName("visibility") val visibility: Int,
        @SerialName("wind_speed") val windSpeed: Double,
        @SerialName("wind_gust") val windGust: Double? = null,
        @SerialName("wind_deg") val windDeg: Int,
        @SerialName("pop") val pop: Double,
        @SerialName("rain") val rain: Rain? = null,
        @SerialName("snow") val snow: Snow? = null,
        @SerialName("weather") val weather: List<Weather>,
        
        ) {
        @Serializable
        data class Rain(
            @SerialName("1h") val precipitation: Double
        )
        
        @Serializable
        data class Snow(
            @SerialName("1h") val precipitation: Double
        )
        
        @Serializable
        data class Weather(
            @SerialName("id") val id: Int,
            @SerialName("main") val main: String,
            @SerialName("description") val description: String,
            @SerialName("icon") val icon: String
        )
    }
    
    @Serializable
    data class Daily(
        @SerialName("dt") val dt: Int,
        @SerialName("sunrise") val sunrise: Int,
        @SerialName("sunset") val sunset: Int,
        @SerialName("moonrise") val moonrise: Int,
        @SerialName("moonset") val moonset: Int,
        @SerialName("moon_phase") val moonPhase: Double,
        @SerialName("summary") val summary: String,
        
        @SerialName("temp") val temp: Temp,
        
        @SerialName("feels_like") val feelsLike: FeelsLike,
        
        @SerialName("pressure") val pressure: Int,
        @SerialName("humidity") val humidity: Int,
        @SerialName("dew_point") val dewPoint: Double,
        @SerialName("wind_speed") val windSpeed: Double ,
        @SerialName("wind_gust") val windGust: Double? = null,
        @SerialName("wind_deg") val windDeg: Int,
        @SerialName("clouds") val clouds: Int,
        @SerialName("uvi") val uvi: Double,
        @SerialName("pop") val pop: Double,
        @SerialName("rain") val rain: Double? = null,
        @SerialName("snow") val snow: Double? = null,
        @SerialName("weather") val weather: List<Weather>,
        ) {
        @Serializable
        data class Temp(
            @SerialName("morn") val morn: Double,
            @SerialName("day") val day: Double,
            @SerialName("eve") val eve: Double,
            @SerialName("night") val night: Double,
            @SerialName("min") val min: Double,
            @SerialName("max") val max: Double,
        )
        
        @Serializable
        data class FeelsLike(
            @SerialName("morn") val morn: Double,
            @SerialName("day") val day: Double,
            @SerialName("eve") val eve: Double,
            @SerialName("night") val night: Double,
        )
        
        @Serializable
        data class Weather(
            @SerialName("id") val id: Int,
            @SerialName("main") val main: String,
            @SerialName("description") val description: String,
            @SerialName("icon") val icon: String
        )
    }

}