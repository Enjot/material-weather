package com.enjot.materialweather.domain.mapper

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult

fun SavedLocation.toSearchResult(): SearchResult {
    return SearchResult(
        city = this.name,
        postCode = this.postCode,
        countryCode = this.countryCode,
        coordinates = this.coordinates
    )
}