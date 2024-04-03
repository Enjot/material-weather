package com.enjot.materialweather.domain.usecase

import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.repository.LocationRepository
import com.enjot.materialweather.domain.utils.Resource
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Resource<Coordinates?> = locationRepository.getCoordinates()
}