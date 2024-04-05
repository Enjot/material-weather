package com.enjot.materialweather.data.repository

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.enjot.materialweather.data.background.UpdateWeatherWorker
import com.enjot.materialweather.domain.repository.WorkScheduler
import kotlinx.coroutines.guava.asDeferred
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkSchedulerImpl @Inject constructor(
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
            "UpdateWeatherWork",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )
    }
    
    override fun cancelWork(workName: String) {
        workManager.cancelUniqueWork(workName)
    }
    
    override suspend fun isWorkScheduled(workName: String): Boolean {
        val workInfosDeferred = workManager.getWorkInfosForUniqueWork(workName).asDeferred()
        val workInfos = workInfosDeferred.await()
        return workInfos.any { it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING }
    }
}
