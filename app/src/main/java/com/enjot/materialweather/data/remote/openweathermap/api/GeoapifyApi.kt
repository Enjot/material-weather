package com.enjot.materialweather.data.remote.openweathermap.api

import com.enjot.materialweather.data.remote.openweathermap.dto.GeocodingDto
import com.enjot.materialweather.data.remote.openweathermap.dto.ReverseGeocodingDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoapifyApi {
    
    /*
     Limit is for 5 results because free plan for this api let me
     make only 5 requests per second. If I exceed this limit, it leads
     to bugs for responses with let's say 9 results.
     I make reversed geocoding calls calls asynchronously for every
     result from this call to get postcode for every result.
     Checked many apis already and this is the only free solution I found.
     */
    
    @GET("v1/geocode/search?limit=5&type=city&format=json&apiKey=$API_KEY_GEOAPIFY")
    suspend fun callGeocodingApi(
    @Query("text") query: String
    ): GeocodingDto
    
    @GET("v1/geocode/reverse?type=street&format=json&apiKey=$API_KEY_GEOAPIFY")
    suspend fun callReverseGeocodingApi(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): ReverseGeocodingDto
    
    companion object {
        const val BASE_URL = "https://api.geoapify.com/"
    }
}