package com.enjot.materialweather.settings.domain

import com.enjot.materialweather.settings.domain.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    fun settingsStateFlow(): Flow<Settings>
    suspend fun toggleBackgroundUpdates()
    suspend fun setBackgroundUpdatesRepeatInterval(repeatInterval: Long)
}