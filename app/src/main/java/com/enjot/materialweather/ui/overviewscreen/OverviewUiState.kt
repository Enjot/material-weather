package com.enjot.materialweather.ui.overviewscreen

import androidx.compose.runtime.Immutable
import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo

@Immutable
data class OverviewUiState(
    val weatherInfo: WeatherInfo? = null,
    val isWeatherLoading: Boolean = false,
    val weatherError: Boolean = false,
    
    // Expandable searchbar banner
    val isSearchBarActive: Boolean = false,
    val query: String = "",
    val isSearchResultsLoading: Boolean = false,
    val searchResultsError: Boolean = false,
    val searchResults: List<SearchResult> = emptyList(),
    val savedLocations: List<SavedLocation> = emptyList()
)