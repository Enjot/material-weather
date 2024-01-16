package com.enjot.materialweather.domain.location

import com.enjot.materialweather.domain.model.Coordinates

interface LocationProvider {
    
    suspend fun getCurrentLocation(): Coordinates?
}