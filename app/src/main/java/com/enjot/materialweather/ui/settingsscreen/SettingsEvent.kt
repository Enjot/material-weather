package com.enjot.materialweather.ui.settingsscreen

sealed class SettingsEvent {

    data object OnBackgroundWeatherUpdatesClick : SettingsEvent()
    data class OnSetIntervals(val minutes: Long): SettingsEvent()
    data object OnLocationBasedClick: SettingsEvent()
}