package com.enjot.materialweather.domain.usecase.weather

import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalWeatherFlow @Inject constructor(
    private val localRepository: LocalRepository
) {
    operator fun invoke(): Flow<WeatherInfo> = localRepository.getLocalWeather()
}
