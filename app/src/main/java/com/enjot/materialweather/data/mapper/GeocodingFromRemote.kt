package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.database.weather.WeatherEntity
import com.enjot.materialweather.data.remote.openweathermap.dto.ReverseGeocodingDto
import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.SearchResult

fun ReverseGeocodingDto.Result.toLocalSearchResult(): WeatherEntity.SearchResult {
    return  WeatherEntity.SearchResult(
        city = city,
        postCode = postcode,
        countryCode = countryCode.uppercase(),
        coordinates = WeatherEntity.SearchResult.Coordinates(this.lat, this.lon)
    )
}

fun ReverseGeocodingDto.Result.toDomainSearchResult(): SearchResult {
    return  SearchResult(
        city = city,
        postCode = postcode,
        countryCode = countryCode.uppercase(),
        coordinates = Coordinates(this.lat, this.lon)
    )
}