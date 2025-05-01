package com.enjot.materialweather.weather.data.remote

import com.enjot.materialweather.BuildConfig

const val API_KEY_OPENWEATHERMAP = BuildConfig.API_KEY_OPENWEATHERMAP
const val API_KEY_GEOAPIFY = BuildConfig.API_KEY_GEOAPIFY

object HttpRoutes {
    private const val GEOAPIFY_BASE_URL = "https://api.geoapify.com"
    const val GEOCODING = "$GEOAPIFY_BASE_URL/v1/geocode/search?limit=5&type=city&format=json&apiKey=$API_KEY_GEOAPIFY"
    const val REVERSED_GEOCODING = "$GEOAPIFY_BASE_URL/v1/geocode/reverse?type=street&format=json&apiKey=$API_KEY_GEOAPIFY"

    private const val OPENWEATHERMAP_BASE_URL = "https://api.openweathermap.org"
    const val ONE_CALL_URL = "$OPENWEATHERMAP_BASE_URL/data/3.0/onecall?&appid=$API_KEY_OPENWEATHERMAP"
    const val AIR_POLLUTION_URL = "$OPENWEATHERMAP_BASE_URL/data/2.5/air_pollution?appid=$API_KEY_OPENWEATHERMAP"
}