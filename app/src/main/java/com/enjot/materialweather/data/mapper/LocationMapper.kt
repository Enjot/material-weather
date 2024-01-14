package com.enjot.materialweather.data.mapper

import android.location.Location
import com.enjot.materialweather.domain.model.LocationInfo

fun Location.toLocationInfo(): LocationInfo {
    return LocationInfo(
        lat = latitude,
        lon = longitude
    )
}