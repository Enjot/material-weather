package com.enjot.materialweather.weather.domain.model

import com.enjot.materialweather.core.domain.Coordinates

data class SearchResult(
    val city: String,
    val postCode: String?,
    val countryCode: String,
    val coordinates: Coordinates
)