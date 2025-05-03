package com.enjot.materialweather.weather.domain.usecase

import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.core.domain.utils.ErrorType
import com.enjot.materialweather.core.domain.utils.Resource
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import com.enjot.materialweather.weather.domain.repository.RemoteRepository

class UpdateWeatherUseCase(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(coordinates: Coordinates): Resource<Unit> {
        
        return when (val remoteResource = remoteRepository.fetchWeather(coordinates)) {
            is Resource.Success -> {
                if (remoteResource.data != null) {
                    localRepository.saveLocalWeather(remoteResource.data!!)
                    Resource.Success()
                } else Resource.Error(ErrorType.UNKNOWN)
            }
            
            is Resource.Error ->
                Resource.Error(remoteResource.errorType ?: ErrorType.UNKNOWN)
        }
    }
}
