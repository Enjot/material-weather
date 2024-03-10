package com.enjot.materialweather.data.mapper

import com.enjot.materialweather.data.local.WeatherEntity
import com.enjot.materialweather.data.remote.openweathermap.dto.AirPollutionDto
import kotlin.math.roundToInt

fun AirPollutionDto.toAirPollutionOrNull(): WeatherEntity.AirPollution? {
    if (innerList.isNotEmpty()) {
        val aqi = innerList[0].main?.aqi
        val co = innerList[0].components?.co
        val no2 = innerList[0].components?.no2
        val o3 = innerList[0].components?.o3
        val pm10 = innerList[0].components?.pm10
        val pm25 = innerList[0].components?.pm25
        val nh3 = innerList[0].components?.nh3
        val so2 = innerList[0].components?.so2
        val no = innerList[0].components?.no
        return WeatherEntity.AirPollution(
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
    } else return null
}
