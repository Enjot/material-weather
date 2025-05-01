package com.enjot.materialweather.weather.domain.model

import com.enjot.materialweather.core.domain.Coordinates

data class SavedLocation(
    val id: Int? = null,
    val name: String,
    val postCode: String? = null,
    val countryCode: String,
    val coordinates: Coordinates
)