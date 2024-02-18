package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.local.WeatherEntity
import com.enjot.materialweather.data.remote.openweathermap.dto.OneCallDto
import kotlin.math.roundToInt

fun OneCallDto.toCurrentWeather(): WeatherEntity.Current {
    return WeatherEntity.Current(
        localFormattedTime = this.current.dt.toFormattedLocalTime(),
        temp = this.current.temp.roundToInt(),
        minTemp = this.daily[0].temp.min.roundToInt(),
        maxTemp = this.daily[0].temp.max.roundToInt(),
        feelsLike = this.current.feelsLike.roundToInt(),
        clouds = this.current.clouds,
        windGust = this.current.windGust,
        rainPrecipitation = this.current.rain?.precipitation,
        snowPrecipitation = this.current.snow?.precipitation,
        weather = this.current.weather[0].main,
        description = this.current.weather[0].description,
        icon = this.current.weather[0].icon,
        conditions = WeatherEntity.Current.Conditions(
            sunrise = this.current.sunrise.toFormattedLocalTime(),
            sunset = this.current.sunset.toFormattedLocalTime(),
            pressure = this.current.pressure,
            humidity = this.current.humidity,
            dewPoint = this.current.dewPoint.roundToInt(),
            uvi = this.current.uvi.roundToInt(),
            windSpeed = this.current.windSpeed.roundToInt(),
            windDeg = this.current.windDeg,
        )
    )
}

fun OneCallDto.toHourlyWeatherList(): List<WeatherEntity.HourlyWeather> {
    return this.hourly.map {
        WeatherEntity.HourlyWeather(
            localFormattedTime = it.dt.toFormattedLocalTime(),
            temp = it.temp.roundToInt(),
            humidity = it.humidity,
            windSpeed = it.windSpeed.roundToInt(),
            windDeg = it.windDeg,
            pop = it.pop,
            rainPrecipitation = it.rain?.precipitation,
            snowPrecipitation = it.snow?.precipitation,
            icon = it.weather[0].icon,
        )
    }
}

fun OneCallDto.toDailyWeatherList(): List<WeatherEntity.DailyWeather> {
    return this.daily.map {
        WeatherEntity.DailyWeather(
            dayOfWeek = it.dt.toDayOfWeek(),
            sunrise = it.sunrise.toFormattedLocalTime(),
            sunset = it.sunset.toFormattedLocalTime(),
            moonrise = it.moonrise.toFormattedLocalTime(),
            moonset = it.moonset.toFormattedLocalTime(),
            moonPhase = it.moonPhase,
            summary = it.summary,
            tempDay = it.temp.day.roundToInt(),
            tempNight = it.temp.night.roundToInt(),
            pressure = it.pressure,
            humidity = it.humidity,
            dewPoint = it.dewPoint.roundToInt(),
            uvi = it.uvi.roundToInt(),
            clouds = it.clouds,
            windSpeed = it.windSpeed.roundToInt(),
            windGust = it.windGust,
            windDeg = it.windDeg,
            pop = it.pop,
            rainPrecipitation = it.rain,
            snowPrecipitation = it.snow,
            weather = it.weather[0].main,
            description = it.weather[0].description,
            icon = it.weather[0].icon,
        )
    }
}