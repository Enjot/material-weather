package com.enjot.materialweather.presentation.ui.screen.settings

import androidx.compose.runtime.Immutable


@Immutable
data class SettingsUiState(
    val isWorkScheduled: Boolean = false,
    val isLocationBased: Boolean = false
)