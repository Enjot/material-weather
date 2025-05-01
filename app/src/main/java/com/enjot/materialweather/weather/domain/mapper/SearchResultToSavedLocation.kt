package com.enjot.materialweather.weather.domain.mapper

import com.enjot.materialweather.weather.domain.model.SavedLocation
import com.enjot.materialweather.weather.domain.model.SearchResult

fun SearchResult.toSavedLocation(): SavedLocation {
    return SavedLocation(
    name = this.city,
    postCode = this.postCode,
    countryCode = this.countryCode,
    coordinates = this.coordinates
    )
}