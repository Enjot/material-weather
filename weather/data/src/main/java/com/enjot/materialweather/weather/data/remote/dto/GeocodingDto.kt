package com.enjot.materialweather.weather.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingDto(
    @SerialName("results") val results: List<Result> = emptyList()
) {
    @Serializable
    data class Result(
        
        @SerialName("lon") val lon: Double,
        @SerialName("lat") val lat: Double,
    )
}