package com.enjot.materialweather.domain.usecase

import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalWeatherUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): Flow<WeatherInfo?> = localRepository.getLocalWeather()
}
