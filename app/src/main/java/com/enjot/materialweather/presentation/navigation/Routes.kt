package com.enjot.materialweather.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Overview

@Serializable
data class Daily(val index: Int)

@Serializable
data object Settings