package com.enjot.materialweather.ui.overviewscreen

import androidx.compose.runtime.Immutable
import com.enjot.materialweather.domain.model.SearchResult

@Immutable
data class OverviewUiState(
    val isWeatherLoading: Boolean = false,
    val weatherError: Boolean = false,
    
    val isSearchBarActive: Boolean = false,
    val query: String = "",
    val isSearchResultsLoading: Boolean = false,
    val searchResultsError: Boolean = false,
    val searchResults: List<SearchResult> = emptyList()
)