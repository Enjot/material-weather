package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.database.SavedLocationEntity
import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.SavedLocation

fun SavedLocationEntity.toSavedLocation(): SavedLocation {
    return SavedLocation(
        this.id,
        this.name,
        this.postCode,
        this.countryCode,
        Coordinates(this.lat, this.lon)
    )
}

fun SavedLocation.toSavedLocationEntity(): SavedLocationEntity {
    return SavedLocationEntity(
        this.id,
        this.name,
        this.postCode,
        this.countryCode,
        this.coordinates.lat,
        this.coordinates.lon
    )
}