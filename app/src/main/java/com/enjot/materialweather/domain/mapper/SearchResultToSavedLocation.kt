package com.enjot.materialweather.domain.mapper

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult

fun SearchResult.toSavedLocation(): SavedLocation {
    return SavedLocation(
    name = this.city,
    postCode = this.postCode,
    countryCode = this.countryCode,
    coordinates = this.coordinates
    )
}