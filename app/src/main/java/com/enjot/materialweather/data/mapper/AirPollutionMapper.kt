package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.remote.openweathermap.dto.AirPollutionDto
import com.enjot.materialweather.domain.model.AirPollution
import kotlin.math.roundToInt

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
        co = co?.roundToInt() ?: return null,
        no2 = no2?.roundToInt() ?: return null,
        o3 = o3?.roundToInt() ?: return null,
        pm10 = pm10?.roundToInt() ?: return null,
        pm25 = pm25?.roundToInt() ?: return null,
        nh3 = nh3?.roundToInt() ?: return null,
        so2 = so2?.roundToInt() ?: return null,
        no = no?.roundToInt() ?: return null
    )
}