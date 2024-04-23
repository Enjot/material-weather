package com.enjot.materialweather.domain.usecase.weather

import com.enjot.materialweather.domain.repository.LocalRepository
import com.enjot.materialweather.domain.repository.LocationRepository
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.utils.ErrorType
import com.enjot.materialweather.domain.utils.Resource
import javax.inject.Inject

class GetWeatherFromLocationUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val locationRepository: LocationRepository,
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): Resource<Unit> {

        return when (val locationResource = locationRepository.getCoordinates()) {

            is Resource.Success -> {
                if (locationResource.data != null) {
                    return when (
                        val remoteResource = remoteRepository.fetchWeather(locationResource.data)
                    ) {
                        is Resource.Success -> {
                            if (remoteResource.data != null) {
                                localRepository.saveLocalWeather(remoteResource.data)
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
