package com.enjot.materialweather.settings.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import com.enjot.materialweather.weather.domain.usecase.weather.UpdateWeatherUseCase
import kotlinx.coroutines.flow.first

const val UPDATE_WEATHER_WORK = "UpdateWeatherWork"

class UpdateWeatherWorker(
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val localRepository: LocalRepository,
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val weatherInfo = localRepository.getLocalWeather().first()
            val coordinates = weatherInfo.place!!.coordinates
            updateWeatherUseCase(coordinates)
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }
}