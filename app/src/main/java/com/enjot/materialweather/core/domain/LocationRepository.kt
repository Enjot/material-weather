package com.enjot.materialweather.core.domain

import com.enjot.materialweather.core.domain.utils.Resource

interface LocationRepository {

    suspend fun getCoordinates(): Resource<Coordinates?>
}