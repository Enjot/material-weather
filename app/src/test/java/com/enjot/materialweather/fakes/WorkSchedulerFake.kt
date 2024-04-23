package com.enjot.materialweather.fakes

import com.enjot.materialweather.domain.repository.WorkScheduler

class WorkSchedulerFake : WorkScheduler {

    var updateWeatherWork = Triple("UpdateWeatherWork", 30L, false)

    override fun scheduleWeatherUpdateWork(repeatInterval: Long) {
        updateWeatherWork = updateWeatherWork.copy(second = repeatInterval, third = true)
    }

    override fun cancelWork(workName: String) {
        if (workName == "UpdateWeatherWork") {
            updateWeatherWork = updateWeatherWork.copy(third = false)
        }
    }

    override suspend fun isWorkScheduled(workName: String): Boolean {
        return if (workName == "UpdateWeatherWork") updateWeatherWork.third else false
    }
}