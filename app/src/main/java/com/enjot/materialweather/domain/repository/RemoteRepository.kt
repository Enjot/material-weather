package com.enjot.materialweather.domain.repository

import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.utils.Resource

interface RemoteRepository {
    
    suspend fun fetchWeather(coordinates: Coordinates): Resource<WeatherInfo>
    
    suspend fun getSearchResults(query: String): Resource<List<SearchResult>>
    
}