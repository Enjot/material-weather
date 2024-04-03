package com.enjot.materialweather.domain.usecase

import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.repository.LocalRepository
import com.enjot.materialweather.domain.repository.RemoteRepository
import javax.inject.Inject

class FetchAndStoreWeatherInfoUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(coordinates: Coordinates) {
        
        val result = remoteRepository.getWeather(coordinates)
        localRepository.saveLocalWeather(result.data)
    }
}
