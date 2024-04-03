package com.enjot.materialweather.domain.usecase

import com.enjot.materialweather.domain.repository.LocalRepository
import com.enjot.materialweather.domain.repository.LocationRepository
import com.enjot.materialweather.domain.repository.RemoteRepository
import javax.inject.Inject

class GetWeatherFromLocationUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val locationRepository: LocationRepository,
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke() {
        val locationResult = locationRepository.getCoordinates()
        
        locationResult.data?.let {
            val remoteResult = remoteRepository.getWeather(it)
            localRepository.saveLocalWeather(remoteResult.data)
        }
    }
}