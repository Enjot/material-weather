package com.enjot.materialweather.domain.usecase

import com.enjot.materialweather.domain.repository.LocalRepository
import com.enjot.materialweather.domain.repository.LocationRepository
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.utils.Resource
import javax.inject.Inject

class GetWeatherFromLocationUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val locationRepository: LocationRepository,
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke() {
        when (val locationResource = locationRepository.getCoordinates()) {
            
            is Resource.Success -> locationResource.data?.let {
                when (val remoteResource = remoteRepository.getWeather(it)) {
                    is Resource.Success -> localRepository.saveLocalWeather(remoteResource.data)
                    is Resource.Error -> return@let
                }
            }
            
            is Resource.Error -> return
        }
    }
}
