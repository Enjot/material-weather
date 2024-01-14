package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.remote.openweathermap.dto.AirPollutionDto
import com.enjot.materialweather.domain.model.AirPollution

fun AirPollutionDto.toAirPollutionOrNull(): AirPollution? {
    val aqi = innerList?.get(0)?.main?.aqi
    val co = innerList?.get(0)?.components?.co
    val no2 = innerList?.get(0)?.components?.no2
    val o3 = innerList?.get(0)?.components?.o3
    val pm10 = innerList?.get(0)?.components?.pm10
    val pm25 = innerList?.get(0)?.components?.pm25
    val nh3 = innerList?.get(0)?.components?.nh3
    val so2 = innerList?.get(0)?.components?.so2
    val no = innerList?.get(0)?.components?.no
    return AirPollution(
        aqi = aqi ?: return null,
        co = co ?: return null,
        no2 = no2 ?: return null,
        o3 = o3 ?: return null,
        pm10 = pm10 ?: return null,
        pm25 = pm25 ?: return null,
        nh3 = nh3 ?: return null,
        so2 = so2 ?: return null,
        no = no ?: return null
    )
}