package com.enjot.materialweather.weather.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReverseGeocodingDto(
    @SerialName("results") val results: List<Result> = emptyList(),
) {
    @Serializable
    data class Result(
        @SerialName("country_code") val countryCode: String,
        @SerialName("city") val city: String,
        @SerialName("postcode") val postcode: String? = null,
        @SerialName("lon") val lon: Double,
        @SerialName("lat") val lat: Double,
    )
}