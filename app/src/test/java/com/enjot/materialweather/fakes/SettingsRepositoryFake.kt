package com.enjot.materialweather.fakes

import com.enjot.materialweather.settings.domain.Settings
import com.enjot.materialweather.settings.domain.SettingsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SettingsMangerFake : SettingsManager {

    val userPreferences = MutableStateFlow(Settings())

    override fun settingsStateFlow(): Flow<Settings> {
        return userPreferences
    }

    override suspend fun toggleBackgroundUpdates() {
        userPreferences.update {
            it.copy(backgroundUpdatesEnabled = !it.backgroundUpdatesEnabled)
        }
    }

    override suspend fun setBackgroundUpdatesRepeatInterval(repeatInterval: Long) {
        userPreferences.update { it.copy(backgroundUpdatesRepeatInterval = repeatInterval) }
    }

}