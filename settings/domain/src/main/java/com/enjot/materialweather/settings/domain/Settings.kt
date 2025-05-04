package com.enjot.materialweather.settings.domain

data class Settings(
    val backgroundUpdatesEnabled: Boolean = false,
    val backgroundUpdatesRepeatInterval: Long = 30L,
    val areLocationBasedBackgroundUpdatesEnabled: Boolean = false
)