package com.enjot.materialweather.ui.overviewscreen

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult

sealed class OverviewEvent {
    
    data class OnQueryChange(val query: String) : OverviewEvent()
    data class OnSearch(val query: String) : OverviewEvent()
    data object OnBannerCollapse : OverviewEvent()
    data class OnSearchResultClick(val searchResult: SearchResult) : OverviewEvent()
    data class OnAddToSaved(val searchResult: SearchResult) : OverviewEvent()
    data class OnDeleteFromSaved(val savedLocation: SavedLocation) : OverviewEvent()
    data object OnLocationButtonClick : OverviewEvent()
    
    
    data object OnSearchBarClick : OverviewEvent()
    data object OnPullRefresh : OverviewEvent()
}

