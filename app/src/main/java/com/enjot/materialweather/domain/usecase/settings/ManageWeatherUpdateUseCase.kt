package com.enjot.materialweather.domain.usecase.settings

import com.enjot.materialweather.domain.repository.PreferencesRepository
import com.enjot.materialweather.domain.repository.WorkScheduler
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ManageWeatherUpdateUseCase @Inject constructor(
    private val workScheduler: WorkScheduler,
    private val preferencesRepository: PreferencesRepository
) {
    val userPreferences = preferencesRepository.getUserPreferencesFlow()
    
    suspend fun schedule() {
        val repeatInterval = userPreferences.first().backgroundUpdatesRepeatInterval
        workScheduler.scheduleWeatherUpdateWork(repeatInterval)
    }
    
    suspend fun updateRepeatInterval(repeatInterval: Long) {
        preferencesRepository.setBackgroundUpdatesRepeatInterval(repeatInterval)
    }
    
    fun cancel() {
        workScheduler.cancelUpdateWeatherWork()
    }
    
    suspend fun isWorkScheduled(): Boolean {
        return workScheduler.isUpdateWeatherWorkScheduled()
    }
}