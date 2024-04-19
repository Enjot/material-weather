package com.enjot.materialweather.domain.util

import com.enjot.materialweather.data.remote.openweathermap.dto.ReverseGeocodingDto

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
