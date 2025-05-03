package com.enjot.materialweather.weather.data.mapper

import com.enjot.materialweather.weather.data.database.entity.WeatherEntity
import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.weather.data.remote.dto.ReverseGeocodingDto
import com.enjot.materialweather.weather.domain.model.SearchResult

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