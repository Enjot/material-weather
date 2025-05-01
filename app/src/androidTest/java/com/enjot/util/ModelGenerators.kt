package com.enjot.util

import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.weather.domain.model.SavedLocation

fun savedLocation() = SavedLocation(
    id = 1,
    name = "Test name",
    postCode = "ABCDE",
    countryCode = "XYZ",
    coordinates = coordinates()
)

fun coordinates() = Coordinates(
    lat = 50.0,
    lon = 50.0
)