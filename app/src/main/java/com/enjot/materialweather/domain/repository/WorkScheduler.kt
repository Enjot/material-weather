package com.enjot.materialweather.domain.repository

interface WorkScheduler {
    
    fun scheduleWeatherUpdateWork(repeatInterval: Long)
    
    fun cancelWork(workName: String)
    
    suspend fun isWorkScheduled(workName: String): Boolean
}