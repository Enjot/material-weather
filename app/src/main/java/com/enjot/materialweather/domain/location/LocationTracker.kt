package com.enjot.materialweather.domain.location

import com.enjot.materialweather.domain.model.LocationInfo

interface LocationTracker {
    
    suspend fun getLastLocation(): LocationInfo?
}