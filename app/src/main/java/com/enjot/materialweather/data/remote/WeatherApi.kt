package com.enjot.materialweather.data.remote

import com.enjot.materialweather.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

const val apiKey = BuildConfig.API_KEY

interface WeatherApi {
    
    @GET("geo/1.0/direct?limit=5&appid=$apiKey")
    suspend fun getCoordinates(
        @Query("q") name: String = "Sieniawa"
    ): ArrayList<SearchResultsDto.SearchResultsDtoItem>
    
    @GET("data/2.5/weather?appid=$apiKey")
    suspend fun currentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String = "eng",
        @Query("units") units: String = "metric"
    ): CurrentWeatherDto
    
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
    }
}