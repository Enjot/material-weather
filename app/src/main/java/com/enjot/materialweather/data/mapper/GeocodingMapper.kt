package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.remote.openweathermap.dto.ReverseGeocodingDto
import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.SearchResult

fun ReverseGeocodingDto.Result.toSearchResult(): SearchResult {
    return  SearchResult(
        city = city,
        postCode = postcode,
        countryCode = countryCode.uppercase(),
        coordinates = Coordinates(this.lat, this.lon)
    )
}