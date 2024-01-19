package com.enjot.materialweather.domain.model

data class SearchResult(
    val city: String,
    val postCode: String,
    val countryCode: String,
    val coordinates: Coordinates
)