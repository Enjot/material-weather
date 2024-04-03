package com.enjot.materialweather.domain.repository

import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.utils.Resource

interface LocationRepository {
    
    suspend fun getCoordinates(): Resource<Coordinates?>
}
