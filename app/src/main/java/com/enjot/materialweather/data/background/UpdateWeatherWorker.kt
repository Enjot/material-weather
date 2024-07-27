package com.enjot.materialweather.data.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.enjot.materialweather.domain.usecase.weather.LocalWeatherFlow
import com.enjot.materialweather.domain.usecase.weather.UpdateWeatherUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

const val UPDATE_WEATHER_WORK = "UpdateWeatherWork"

@HiltWorker
class UpdateWeatherWorker @AssistedInject constructor(
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val localWeatherFlow: LocalWeatherFlow,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
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
