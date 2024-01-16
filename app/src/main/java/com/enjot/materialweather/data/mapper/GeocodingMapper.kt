package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.remote.openweathermap.dto.GeocodingDto
import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.SearchResult

fun GeocodingDto.toSearchResult(): SearchResult {
    return  SearchResult(
        name = this.name,
        coordinates = Coordinates(this.lat, this.lon)
    )
}