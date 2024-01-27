package com.enjot.materialweather.domain.model

import java.time.LocalDateTime

data class WeatherInfo(
    val place: SearchResult? = null,
    val current: CurrentWeather? = null,
    val hourly: List<HourlyWeather>? = null,
    val daily: List<DailyWeather>? = null,
    val airPollution: AirPollution? = null
)
