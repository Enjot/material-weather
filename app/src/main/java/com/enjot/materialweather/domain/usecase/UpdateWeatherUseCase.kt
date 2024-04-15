package com.enjot.materialweather.domain.usecase

import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.repository.LocalRepository
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.utils.Resource
import javax.inject.Inject

class UpdateWeatherUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(coordinates: Coordinates): Boolean {
        
        return when (val remoteResource = remoteRepository.getWeather(coordinates)) {
            is Resource.Success -> {
                localRepository.saveLocalWeather(remoteResource.data)
                true
            }
            
            is Resource.Error -> false
        }
    }
}
