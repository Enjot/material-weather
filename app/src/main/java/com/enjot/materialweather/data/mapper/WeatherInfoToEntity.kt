package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.database.weather.WeatherEntity
import com.enjot.materialweather.domain.model.WeatherInfo

fun WeatherInfo.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        id = 1,
        place = this.place?.let {
            WeatherEntity.SearchResult(
                it.city,
                this.place.postCode,
                this.place.countryCode,
                WeatherEntity.SearchResult.Coordinates(
                    this.place.coordinates.lat,
                    this.place.coordinates.lon
                )
            )
        },
        current = this.current?.let {
            WeatherEntity.Current(
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
                conditions = WeatherEntity.Current.Conditions(
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
                WeatherEntity.HourlyWeather(
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
                WeatherEntity.DailyWeather(
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
        airPollution =this.airPollution?.let {
            WeatherEntity.AirPollution(
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