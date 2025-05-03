package com.enjot.materialweather.weather.data.util

import com.enjot.materialweather.weather.data.remote.dto.OneCallDto
import com.enjot.materialweather.weather.data.remote.dto.ReverseGeocodingDto

fun reverseGeocodingDto() = ReverseGeocodingDto(
    results = listOf(
        ReverseGeocodingDto.Result(
            countryCode = "Mongolia",
            city = "Stepford",
            postcode = null,
            lon = 32.33,
            lat = 34.35
        )
    )
)

fun oneCallDto() = OneCallDto(
    lat = 14.15, lon = 16.17, timezone = "taciti", timezoneOffset = 3735, current = OneCallDto.Current(
        dt = 6116,
        sunrise = 2216,
        sunset = 6394,
        temp = 18.19,
        feelsLike = 20.21,
        pressure = 1199,
        humidity = 1915,
        dewPoint = 22.23,
        clouds = 8333,
        uvi = 24.25,
        visibility = 7712,
        windSpeed = 26.27,
        windGust = null,
        windDeg = 9843,
        rain = null,
        snow = null,
        weather = listOf()
    ), hourly = listOf(), daily = listOf()

)