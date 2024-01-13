package com.enjot.materialweather.domain

import com.enjot.materialweather.data.remote.CurrentWeatherDto

interface WeatherRepository {

    suspend fun getCurrentWeather(name: String = "Sieniawa"): CurrentWeatherDto
    
}