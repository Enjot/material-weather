package com.enjot.materialweather.ui.screen.overview

import androidx.compose.runtime.Immutable
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.ui.utils.UiText

@Immutable
data class OverviewUiState(
    val isSearchBannerExpanded: Boolean = false,
    
    var query: String = "",
    
    val weatherState: WeatherState = WeatherState.Idle,
    val searchState: SearchState = SearchState.Idle(emptyList())
)

sealed interface WeatherState {
    
    data object Idle: WeatherState
    data object Loading: WeatherState
    data class Error(val message: UiText): WeatherState
    
}

sealed interface SearchState {
    
    data class Idle(val results: List<SearchResult> = emptyList()) : SearchState
    data object Loading: SearchState
    data class Error(val message: UiText) : SearchState
}
