package com.enjot.materialweather.fakes

import com.enjot.materialweather.data.background.UPDATE_WEATHER_WORK
import com.enjot.materialweather.domain.repository.WorkScheduler

class WorkSchedulerFake : WorkScheduler {

    var updateWeatherWork = Triple(UPDATE_WEATHER_WORK, 30L, false)

    override fun scheduleWeatherUpdateWork(repeatInterval: Long) {
        updateWeatherWork = updateWeatherWork.copy(second = repeatInterval, third = true)
    }

    override fun cancelUpdateWeatherWork() {
            updateWeatherWork = updateWeatherWork.copy(third = false)
    }

    override suspend fun isUpdateWeatherWorkScheduled(): Boolean {
        return updateWeatherWork.third
    }
}