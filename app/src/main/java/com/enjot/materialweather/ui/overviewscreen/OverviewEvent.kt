package com.enjot.materialweather.ui.overviewscreen

import com.enjot.materialweather.domain.model.SearchResult

sealed class OverviewEvent {
    
    sealed class SearchBanner: OverviewEvent() {
        data class OnQueryChange(val query: String): SearchBanner()
        data class OnSearch(val query: String): SearchBanner()
        data object OnBannerCollapse: SearchBanner()
        data class OnSearchResultClick(val searchResult: SearchResult): SearchBanner()
        data class OnAddToFavorites(val searchResult: SearchResult): SearchBanner()
        data object OnCurrentLocationButtonClick: SearchBanner()
    }
    
    data object OnSearchBarClick: OverviewEvent()
    data object OnPullRefresh: OverviewEvent()
    data object OnSnackbarSettingsClick: OverviewEvent()
    data object OnWeatherProviderClick: OverviewEvent()
    data object OnDailyCardClick: OverviewEvent()
}

