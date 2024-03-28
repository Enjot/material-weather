package com.enjot.materialweather.domain.model

data class SavedLocation(
    val id: Int? = null,
    val name: String,
    val postCode: String? = null,
    val countryCode: String,
    val coordinates: Coordinates
)