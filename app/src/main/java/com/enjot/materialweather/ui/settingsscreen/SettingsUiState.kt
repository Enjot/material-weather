package com.enjot.materialweather.ui.settingsscreen

import androidx.compose.runtime.Immutable


@Immutable
data class SettingsUiState(
    val isWorkScheduled: Boolean = false,
    val isLocationBased: Boolean = false
)