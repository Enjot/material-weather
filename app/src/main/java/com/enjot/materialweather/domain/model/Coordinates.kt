package com.enjot.materialweather.domain.model

import android.location.Location

/*
The point to not use android.location.Location class is make it easier
in the future to convert this project to Kotlin Multiplatform one
 */

data class Coordinates(
    val lat: Double,
    val lon: Double
)
