package com.enjot.materialweather.data.mapper

import android.location.Location
import com.enjot.materialweather.domain.model.Coordinates

fun Location.toLocationInfo(): Coordinates {
    return Coordinates(
        lat = latitude,
        lon = longitude
    )
}