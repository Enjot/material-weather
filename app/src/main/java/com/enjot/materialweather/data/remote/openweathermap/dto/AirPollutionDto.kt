package com.enjot.materialweather.data.remote.openweathermap.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionDto(
    @SerialName("coord") val coord: Coord? = null,
    @SerialName("list") val innerList: List<InnerList?>?  = null
) {
    @Serializable
    data class Coord(
        @SerialName("lat") val lat: Double? = null,
        @SerialName("lon") val lon: Double? = null,
    )
    @Serializable
    data class InnerList(
        @SerialName("dt") val dt: Int? = null,
        @SerialName("main") val main: Main? = null,
        @SerialName("components") val components: Components? = null
    ) {
        @Serializable
        data class Main(
            @SerialName("aqi") val aqi: Int? = null
        )

        @Serializable
        data class Components(
            @SerialName("co") val co: Double? = null,
            @SerialName("no") val no: Double? = null,
            @SerialName("no2") val no2: Double? = null,
            @SerialName("o3") val o3: Double? = null,
            @SerialName("so2") val so2: Double? = null,
            @SerialName("pm2_5") val pm25: Double? = null,
            @SerialName("pm10") val pm10: Double? = null,
            @SerialName("nh3") val nh3: Double? = null
        )
    }
}