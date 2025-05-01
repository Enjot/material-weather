package com.enjot.materialweather.weather.data.mapper

import com.enjot.materialweather.weather.data.database.entity.SavedLocationEntity
import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.weather.domain.model.SavedLocation

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