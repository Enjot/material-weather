package com.enjot.materialweather.domain.repository

interface WorkScheduler {
    
    fun scheduleWeatherUpdateWork(repeatInterval: Long)
    
    fun cancelUpdateWeatherWork()
    
    suspend fun isUpdateWeatherWorkScheduled(): Boolean
}