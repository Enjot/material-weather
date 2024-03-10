package com.enjot.materialweather.ui.overviewscreen

import androidx.compose.runtime.Immutable
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo

@Immutable
data class OverviewUiState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    
    // Expandable searchbar banner
    val isSearchBarActive: Boolean = false,
    val query: String = "",
    val searchResults: List<SearchResult> = emptyList()
)