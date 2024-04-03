package com.enjot.materialweather.ui.overviewscreen

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult

sealed class OverviewIntent {
    
    data class OnQueryChange(val query: String) : OverviewIntent()
    data class OnSearch(val query: String) : OverviewIntent()
    data object OnBannerCollapse : OverviewIntent()
    data class OnSearchResultClick(val searchResult: SearchResult) : OverviewIntent()
    data class OnAddToSaved(val searchResult: SearchResult) : OverviewIntent()
    data class OnDeleteFromSaved(val savedLocation: SavedLocation) : OverviewIntent()
    data object OnLocationButtonClick : OverviewIntent()
    
    
    data object OnSearchBarClick : OverviewIntent()
    data object OnPullRefresh : OverviewIntent()
}

