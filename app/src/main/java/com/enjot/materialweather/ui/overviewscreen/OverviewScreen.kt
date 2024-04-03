package com.enjot.materialweather.ui.overviewscreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    val state = viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val onIntent = viewModel::onIntent
    
    // onRefresh function is activated only from pull gesture
    // if refreshing gets true by other way, onRefresh is not getting called
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.value.isWeatherLoading,
        onRefresh = { viewModel.onIntent(OverviewIntent.OnPullRefresh) },
        refreshThreshold = 50.dp,
        refreshingOffset = 200.dp
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        
        Column {
            
            ExpandableSearchBanner(
                query = state.value.query,
                onQueryChange = { onIntent(OverviewIntent.OnQueryChange(it)) },
                selectedCity = state.value.weatherInfo?.place?.city ?: "Search",
                onSearch = { onIntent(OverviewIntent.OnSearch(state.value.query)) },
                isActive = state.value.isSearchBarActive,
                onSearchBarClick = { onIntent(OverviewIntent.OnSearchBarClick) },
                onLocationButtonClick = { onIntent(OverviewIntent.OnLocationButtonClick) },
                onArrowBackClick = { onIntent(OverviewIntent.OnBannerCollapse) },
                onAddToSaved = { onIntent(OverviewIntent.OnAddToSaved(it)) },
                onNavigateToSettings = onNavigateToSettings,
                onSearchResultClick = { onIntent(OverviewIntent.OnSearchResultClick(it)) },
                onRemoveFromSaved = { onIntent(OverviewIntent.OnDeleteFromSaved(it)) },
                searchResults = state.value.searchResults,
                savedLocations = state.value.savedLocations
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            AnimatedVisibility(
                enter = fadeIn(),
                exit = fadeOut(),
                visible = state.value.isWeatherLoading.not() and (state.value.weatherInfo != null)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    state.value.weatherInfo?.current?.let { SummaryBanner(it) }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    state.value.weatherInfo?.hourly?.let { HourlyBanner(it) }
                    
                    state.value.weatherInfo?.daily?.let { DailyBanner(it, onNavigateToDailyWeather) }
                    
                    state.value.weatherInfo?.current?.conditions?.let { ConditionsBanner(it) }
                    
                    state.value.weatherInfo?.airPollution?.let { AirPollutionBanner(it) }
                    
                    WeatherProviderButton(
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(24.dp)
                    )
                }
            }
        }
        if (!state.value.isSearchBarActive) {
            PullRefreshIndicator(
                refreshing = state.value.isWeatherLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
    
    BackHandler(enabled = state.value.isSearchBarActive) {
        onIntent(OverviewIntent.OnBannerCollapse)
    }
}