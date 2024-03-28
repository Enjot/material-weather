package com.enjot.materialweather.ui.overviewscreen

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult

sealed class OverviewEvent {
    
    sealed class SearchBanner: OverviewEvent() {
        data class OnQueryChange(val query: String): SearchBanner()
        data class OnSearch(val query: String): SearchBanner()
        data object OnBannerCollapse: SearchBanner()
        data class OnSearchResultClick(val searchResult: SearchResult): SearchBanner()
        data class OnAddToSaved(val searchResult: SearchResult): SearchBanner()
        data class OnRemoveFromSaved(val savedLocation: SavedLocation): SearchBanner()
        data object OnCurrentLocationButtonClick: SearchBanner()
    }
    
    data object OnSearchBarClick: OverviewEvent()
    data object OnPullRefresh: OverviewEvent()
    data object OnSnackbarSettingsClick: OverviewEvent()
    data object OnWeatherProviderClick: OverviewEvent()
    data object OnDailyCardClick: OverviewEvent()
}

