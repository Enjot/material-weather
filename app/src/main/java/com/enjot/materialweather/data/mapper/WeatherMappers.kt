package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.remote.openweathermap.dto.OneCallDto
import com.enjot.materialweather.domain.model.CurrentWeather
import com.enjot.materialweather.domain.model.DailyWeather
import com.enjot.materialweather.domain.model.HourlyWeather

fun OneCallDto.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        localDateTime = this.current.dt.toLocalDateTime(),
        sunrise = this.current.sunrise.toLocalTime(),
        sunset = this.current.sunset.toLocalTime(),
        temp = this.current.temp,
        feelsLike = this.current.feelsLike,
        pressure = this.current.pressure,
        humidity = this.current.humidity,
        dewPoint = this.current.dewPoint,
        clouds = this.current.clouds,
        uvi = this.current.uvi,
        visibility = this.current.visibility,
        windSpeed = this.current.windSpeed,
        windGust = this.current.windGust!!,
        windDeg = this.current.windDeg,
        rainPrecipitation = this.current.rain?.precipitation,
        snowPrecipitation = this.current.snow?.precipitation,
        weather = this.current.weather[0].main,
        description = this.current.weather[0].description,
        icon = this.current.weather[0].icon
    )
}

fun OneCallDto.toHourlyWeatherList(): List<HourlyWeather> {
    return this.hourly.map {
        HourlyWeather(
            localTime = it.dt.toLocalTime(),
            temp = it.temp,
            feelsLike = it.feelsLike,
            pressure = it.pressure,
            humidity = it.humidity,
            dewPoint = it.dewPoint,
            uvi = it.uvi,
            clouds = it.clouds,
            visibility = it.visibility,
            windSpeed = it.windSpeed,
            windGust = it.windGust,
            windDeg = it.windDeg,
            pop = it.pop,
            rainPrecipitation = it.rain?.precipitation,
            snowPrecipitation = it.snow?.precipitation,
            weather = it.weather[0].main,
            description = it.weather[0].description,
            icon = it.weather[0].icon,
        )
    }
}

fun OneCallDto.toDailyWeatherList(): List<DailyWeather> {
    return this.daily.map {
        DailyWeather(
            localDate = it.dt.toLocalDate(),
            sunrise = it.sunrise.toLocalTime(),
            sunset = it.sunset.toLocalTime(),
            moonrise = it.moonrise.toLocalTime(),
            moonset = it.moonset.toLocalTime(),
            moonPhase = it.moonPhase,
            summary = it.summary,
            tempDay = it.temp.day,
            tempNight = it.temp.night,
            pressure = it.pressure,
            humidity = it.humidity,
            dewPoint = it.dewPoint,
            uvi = it.uvi,
            clouds = it.clouds,
            windSpeed = it.windSpeed,
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
