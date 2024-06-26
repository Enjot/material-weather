package com.enjot.materialweather.domain.model

data class WeatherInfo(
    val place: SearchResult? = null,
    val current: CurrentWeather? = null,
    val hourly: List<HourlyWeather>? = null,
    val daily: List<DailyWeather>? = null,
    val airPollution: AirPollution? = null
) {
    companion object {
        fun empty() = WeatherInfo()
    }
}
