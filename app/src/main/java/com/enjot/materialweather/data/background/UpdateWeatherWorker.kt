package com.enjot.materialweather.data.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.enjot.materialweather.domain.usecase.weather.LocalWeatherFlow
import com.enjot.materialweather.domain.usecase.weather.UpdateWeatherUseCase
import kotlinx.coroutines.flow.first

const val UPDATE_WEATHER_WORK = "UpdateWeatherWork"


class UpdateWeatherWorker(
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val localWeatherFlow: LocalWeatherFlow,
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val weatherInfo = localWeatherFlow().first()
            val coordinates = weatherInfo.place!!.coordinates
            updateWeatherUseCase(coordinates)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
