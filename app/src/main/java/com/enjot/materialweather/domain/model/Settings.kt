package com.enjot.materialweather.domain.model

data class Settings(
    val backgroundUpdatesEnabled: Boolean = false,
    val backgroundUpdatesRepeatInterval: Long = 30L,
    val areLocationBasedBackgroundUpdatesEnabled: Boolean = false
)