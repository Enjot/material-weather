package com.enjot.materialweather.ui.overviewscreen

import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo

data class OverviewUiState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    
    val isSearchBarActive: Boolean = false,
    val query: String = "",
    val searchResults: List<SearchResult> = emptyList()
)