package com.enjot.materialweather.data.remote.openweathermap.api

import com.enjot.materialweather.data.remote.openweathermap.dto.AirPollutionDto
import com.enjot.materialweather.data.remote.openweathermap.dto.OneCallDto
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherMapApi {
    
    @GET("data/3.0/onecall?&appid=$API_KEY_OPENWEATHERMAP")
    suspend fun callOneCallApi(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String = "eng",
        @Query("units") units: String = "metric", // https://openweathermap.org/weather-data
        @Query("exclude") exclude: String = "minutely,alerts"
    ): OneCallDto
    
    @GET("data/2.5/air_pollution?appid=$API_KEY_OPENWEATHERMAP")
    suspend fun callAirPollutionApi(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
    ): AirPollutionDto
    
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
    }
}

