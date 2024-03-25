package com.enjot.materialweather.ui.overviewscreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.enjot.materialweather.ui.overviewscreen.air.AirPollutionBanner
import com.enjot.materialweather.ui.overviewscreen.components.WeatherProviderButton
import com.enjot.materialweather.ui.overviewscreen.conditions.ConditionsBanner
import com.enjot.materialweather.ui.overviewscreen.daily.DailyBanner
import com.enjot.materialweather.ui.overviewscreen.hourly.HourlyBanner
import com.enjot.materialweather.ui.overviewscreen.search.ExpandableSearchBanner
import com.enjot.materialweather.ui.overviewscreen.summary.SummaryBanner
import com.enjot.materialweather.ui.pullrefresh.PullRefreshIndicator
import com.enjot.materialweather.ui.pullrefresh.pullRefresh
import com.enjot.materialweather.ui.pullrefresh.rememberPullRefreshState

@Composable
fun OverviewScreen(
    onNavigateToDailyWeather: (Int) -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scrollState = rememberScrollState()
    val onEvent = viewModel::onEvent
    
    // onRefresh function is activated only from pull gesture
    // if refreshing gets true by other way, onRefresh is not getting called
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.state.isLoading,
        onRefresh = { viewModel.onEvent(OverviewEvent.OnPullRefresh) },
        refreshThreshold = 50.dp,
        refreshingOffset = 200.dp
    )
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pullRefresh(pullRefreshState)
    ) {
        
        Column {
            
            ExpandableSearchBanner(
                query = state.query,
                onQueryChange = { onEvent(OverviewEvent.SearchBanner.OnQueryChange(it)) },
                selectedCity = state.weatherInfo?.place?.city ?: "Search",
                onSearch = { onEvent(OverviewEvent.SearchBanner.OnSearch(state.query)) },
                isActive = state.isSearchBarActive,
                onSearchBarClick = { onEvent(OverviewEvent.OnSearchBarClick) },
                onUseCurrentLocationClick = { onEvent(OverviewEvent.SearchBanner.OnCurrentLocationButtonClick) },
                onArrowBackClick = { onEvent(OverviewEvent.SearchBanner.OnBannerCollapse) },
                onAddToFavorites = { onEvent(OverviewEvent.SearchBanner.OnAddToFavorites(it)) },
                onNavigateToSettings = onNavigateToSettings,
                onSearchResultClick = { onEvent(OverviewEvent.SearchBanner.OnSearchResultClick(it))},
                searchResults = viewModel.state.searchResults,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            AnimatedVisibility(
                enter = fadeIn(),
                exit = fadeOut(),
                visible = state.isLoading.not() and (state.weatherInfo != null)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    state.weatherInfo?.current?.let { SummaryBanner(it) }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    state.weatherInfo?.hourly?.let { HourlyBanner(it) }
                    
                    state.weatherInfo?.daily?.let { DailyBanner(it, onNavigateToDailyWeather) }
                    
                    state.weatherInfo?.current?.conditions?.let { ConditionsBanner(it) }
                    
                    state.weatherInfo?.airPollution?.let { AirPollutionBanner(it) }
                    
                    WeatherProviderButton(Modifier.align(Alignment.CenterHorizontally).padding(24.dp))
                }
            }
        }
        
        PullRefreshIndicator(
            refreshing = state.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
    
    BackHandler(enabled = state.isSearchBarActive) {
        onEvent(OverviewEvent.SearchBanner.OnBannerCollapse)
    }
}