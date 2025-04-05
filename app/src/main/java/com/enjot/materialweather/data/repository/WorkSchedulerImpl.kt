package com.enjot.materialweather.data.repository

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.enjot.materialweather.data.background.UPDATE_WEATHER_WORK
import com.enjot.materialweather.data.background.UpdateWeatherWorker
import com.enjot.materialweather.domain.repository.WorkScheduler
import kotlinx.coroutines.guava.asDeferred
import java.util.concurrent.TimeUnit

class WorkSchedulerImpl(
    private val workManager: WorkManager
) : WorkScheduler {
    override fun scheduleWeatherUpdateWork(repeatInterval: Long) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        
        val workRequest = PeriodicWorkRequestBuilder<UpdateWeatherWorker>(repeatInterval, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        
        workManager.enqueueUniquePeriodicWork(
            UPDATE_WEATHER_WORK,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )
    }

    override fun cancelUpdateWeatherWork() {
        workManager.cancelUniqueWork(UPDATE_WEATHER_WORK)
    }
    
    override suspend fun isUpdateWeatherWorkScheduled(): Boolean {
        val workInfosDeferred = workManager.getWorkInfosForUniqueWork(UPDATE_WEATHER_WORK).asDeferred()
        val workInfos = workInfosDeferred.await()
        return workInfos.any { it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING }
    }
}