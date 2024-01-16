package com.enjot.materialweather.presentation.overview

import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo

data class OverviewState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)