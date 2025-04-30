package com.enjot.materialweather.domain.repository

import com.enjot.materialweather.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    fun settingsStateFlow(): Flow<Settings>
    suspend fun toggleBackgroundUpdates()
    suspend fun setBackgroundUpdatesRepeatInterval(repeatInterval: Long)
}