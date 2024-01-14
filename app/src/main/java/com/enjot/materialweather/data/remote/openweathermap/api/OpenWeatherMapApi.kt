package com.enjot.materialweather.data.remote.openweathermap.api

import com.enjot.materialweather.BuildConfig
import com.enjot.materialweather.data.remote.openweathermap.dto.AirPollutionDto
import com.enjot.materialweather.data.remote.openweathermap.dto.CurrentWeatherDto
import com.enjot.materialweather.data.remote.openweathermap.dto.GeocodingDto
import com.enjot.materialweather.data.remote.openweathermap.dto.OneCallDto
import retrofit2.http.GET
import retrofit2.http.Query

const val apiKey = BuildConfig.API_KEY

interface OpenWeatherMapApi {
    
    @GET("geo/1.0/direct?appid=$apiKey")
    suspend fun getGeocodingApi(
        @Query("q") query: String = "Sieniawa",
        @Query("limit") limit: String = "5"
    ): List<GeocodingDto>
    
    @GET("data/2.5/weather?appid=$apiKey")
    suspend fun getCurrentWeatherApi(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String = "eng",
        @Query("units") units: String = "metric"
    ): CurrentWeatherDto
    
    @GET("data/3.0/onecall?exclude=minutely&exclude=alerts&appid=$apiKey")
    suspend fun getOneCallApi(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String = "eng",
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "minutely,alerts"
    ): OneCallDto
    
    @GET("data/2.5/air_pollution?appid=$apiKey")
    suspend fun getAirPollutionApi(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
    ): AirPollutionDto
    
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
    }
}

