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
    
    fun cancel(workName: String) {
        workScheduler.cancelWork(workName)
    }
    
    suspend fun isWorkScheduled(workName: String): Boolean {
        return workScheduler.isWorkScheduled(workName)
    }
}
