package com.enjot.materialweather.weather.data.mapper

import android.location.Location
import com.enjot.materialweather.core.domain.Coordinates

fun Location.toCoordinates(): Coordinates {
    return Coordinates(
        lat = latitude,
        lon = longitude
    )
}
