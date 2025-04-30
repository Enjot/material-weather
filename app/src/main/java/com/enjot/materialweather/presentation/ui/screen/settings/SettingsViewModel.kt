package com.enjot.materialweather.presentation.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.model.Settings
import com.enjot.materialweather.domain.repository.SettingsManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val settingsManager: SettingsManager,
) : ViewModel() {

    val state: StateFlow<Settings> = settingsManager.settingsStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            Settings()
        )

    fun toggleBackgroundUpdatesEnabled() {
        viewModelScope.launch {
            settingsManager.toggleBackgroundUpdates()
        }
    }

    fun setBackgroundUpdatesInterval(minutes: Long) {
        viewModelScope.launch {
            settingsManager.setBackgroundUpdatesRepeatInterval(minutes)
        }
    }
}