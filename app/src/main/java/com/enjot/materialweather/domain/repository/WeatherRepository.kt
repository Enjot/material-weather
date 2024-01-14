package com.enjot.materialweather.domain.repository

import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.utils.Resource

interface WeatherRepository {

    suspend fun getWeatherInfo(query: String = "Sieniawa"): Resource<WeatherInfo>
    
}