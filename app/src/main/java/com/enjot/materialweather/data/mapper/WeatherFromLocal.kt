package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.database.WeatherEntity
import com.enjot.materialweather.domain.model.AirPollution
import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.CurrentWeather
import com.enjot.materialweather.domain.model.DailyWeather
import com.enjot.materialweather.domain.model.HourlyWeather
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo

fun WeatherEntity.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        place = this.place?.let {
            SearchResult(
                it.city,
                it.postCode,
                it.countryCode,
                Coordinates(
                    it.coordinates.lat,
                    it.coordinates.lon
                )
            )
        },
        current = this.current?.let {
            CurrentWeather(
                it.localFormattedTime,
                it.temp,
                it.minTemp,
                it.maxTemp,
                it.feelsLike,
                it.clouds,
                it.windGust,
                it.rainPrecipitation,
                it.snowPrecipitation,
                it.weather,
                it.description,
                it.icon,
                conditions = CurrentWeather.WeatherConditions(
                    it.conditions.sunrise,
                    it.conditions.sunset,
                    it.conditions.windSpeed,
                    it.conditions.windDeg,
                    it.conditions.humidity,
                    it.conditions.dewPoint,
                    it.conditions.pressure,
                    it.conditions.uvi
                )
            )
        },
        hourly = this.hourly?.let { item ->
            item.map {
                HourlyWeather(
                    it.localFormattedTime,
                    it.temp,
                    it.humidity,
                    it.windSpeed,
                    it.windDeg,
                    it.pop,
                    it.rainPrecipitation,
                    it.snowPrecipitation,
                    it.icon
                )
            }
        },
        daily = this.daily?.let { item ->
            item.map {
                DailyWeather(
                    it.dayOfWeek,
                    it.sunrise,
                    it.sunset,
                    it.moonrise,
                    it.moonset,
                    it.moonPhase,
                    it.summary,
                    it.tempDay,
                    it.tempNight,
                    it.pressure,
                    it.humidity,
                    it.dewPoint,
                    it.uvi,
                    it.clouds,
                    it.windSpeed,
                    it.windGust,
                    it.windDeg,
                    it.pop,
                    it.rainPrecipitation,
                    it.snowPrecipitation,
                    it.weather,
                    it.description,
                    it.icon
                )
            }
        },
        airPollution = this.airPollution?.let {
            AirPollution(
                it.aqi,
                it.nh3,
                it.no,
                it.co,
                it.no2,
                it.o3,
                it.pm10,
                it.pm25,
                it.so2
            )
        }
    )
}