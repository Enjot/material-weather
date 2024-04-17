package com.enjot.materialweather.domain.util

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.util.coordinates

fun savedLocation() = SavedLocation(
    id = 1,
    name = "test name",
    postCode = "test post code",
    countryCode = "test country code",
    coordinates = coordinates()
)