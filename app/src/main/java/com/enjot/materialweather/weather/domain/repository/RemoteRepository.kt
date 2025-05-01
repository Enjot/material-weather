package com.enjot.materialweather.weather.domain.repository

import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.core.domain.utils.Resource
import com.enjot.materialweather.weather.domain.model.SearchResult
import com.enjot.materialweather.weather.domain.model.WeatherInfo

interface RemoteRepository {
    
    suspend fun fetchWeather(coordinates: Coordinates): Resource<WeatherInfo>
    
    suspend fun getSearchResults(query: String): Resource<List<SearchResult>>
    
}