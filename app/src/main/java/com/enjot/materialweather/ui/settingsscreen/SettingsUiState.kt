package com.enjot.materialweather.ui.settingsscreen

import androidx.compose.runtime.Immutable


@Immutable
data class SettingsUiState(
    val isWorkScheduled: Boolean = false,
    val repeatInterval: Long = 30L,
    val isLocationBased: Boolean = false
)