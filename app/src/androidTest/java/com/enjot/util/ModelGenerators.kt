package com.enjot.util

import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.SavedLocation

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