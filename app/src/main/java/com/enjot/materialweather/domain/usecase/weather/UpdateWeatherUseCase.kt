package com.enjot.materialweather.domain.usecase.weather

import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.repository.LocalRepository
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.utils.ErrorType
import com.enjot.materialweather.domain.utils.Resource
import javax.inject.Inject

class UpdateWeatherUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(coordinates: Coordinates): Resource<Unit> {
        
        return when (val remoteResource = remoteRepository.fetchWeather(coordinates)) {
            is Resource.Success -> {
                if (remoteResource.data != null) {
                    localRepository.saveLocalWeather(remoteResource.data)
                    Resource.Success()
                } else Resource.Error(ErrorType.UNKNOWN)
            }
            
            is Resource.Error ->
                Resource.Error(remoteResource.errorType ?: ErrorType.UNKNOWN)
        }
    }
}
