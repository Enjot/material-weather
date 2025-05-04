package com.enjot.materialweather.weather.domain.usecase

import com.enjot.materialweather.core.domain.LocationRepository
import com.enjot.materialweather.core.domain.utils.ErrorType
import com.enjot.materialweather.core.domain.utils.Resource
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import com.enjot.materialweather.weather.domain.repository.RemoteRepository

class GetWeatherFromLocationUseCase(
    private val remoteRepository: RemoteRepository,
    private val locationRepository: LocationRepository,
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): Resource<Unit> {

        return when (val locationResource = locationRepository.getCoordinates()) {

            is Resource.Success -> {
                if (locationResource.data != null) {
                    return when (
                        val remoteResource = remoteRepository.fetchWeather(locationResource.data!!)
                    ) {
                        is Resource.Success -> {
                            if (remoteResource.data != null) {
                                localRepository.saveLocalWeather(remoteResource.data!!)
                                Resource.Success()
                            } else Resource.Error(ErrorType.UNKNOWN)
                        }

                        is Resource.Error -> Resource.Error(
                            remoteResource.errorType ?: ErrorType.UNKNOWN
                        )
                    }
                } else Resource.Error(ErrorType.LOCATION)
            }

            is Resource.Error -> Resource.Error(ErrorType.LOCATION)
        }
    }
}
