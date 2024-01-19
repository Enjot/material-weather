package com.enjot.materialweather.domain.location

import com.enjot.materialweather.domain.model.Coordinates

interface LocationTracker {
    
    suspend fun getCurrentLocation(): Coordinates?
    
    fun arePermissionsGranted(): Boolean

}