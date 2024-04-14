package com.enjot.materialweather.ui.overviewscreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.enjot.materialweather.ui.overviewscreen.air.AirPollutionBanner
import com.enjot.materialweather.ui.overviewscreen.components.WeatherProviderButton
import com.enjot.materialweather.ui.overviewscreen.conditions.ConditionsBanner
import com.enjot.materialweather.ui.overviewscreen.daily.DailyBanner
import com.enjot.materialweather.ui.overviewscreen.hourly.HourlyBanner
import com.enjot.materialweather.ui.overviewscreen.search.ExpandableSearchBanner
import com.enjot.materialweather.ui.overviewscreen.summary.SummaryBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onNavigateToDailyWeather: (Int) -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val onEvent = viewModel::onEvent
    
    val pullRefreshState = rememberPullToRefreshState()
    
    val scaleFraction = if (pullRefreshState.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullRefreshState.progress).coerceIn(0f, 1f)
    
    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(true) {  if(!state.isWeatherLoading) onEvent(OverviewEvent.OnPullRefresh) }
    }
    
    // I use pull to refresh animation for loading data event even not from pull gesture
    // but I'm not sure it's good way to handle it
    LaunchedEffect(state.isWeatherLoading) {
        if (state.isWeatherLoading) pullRefreshState.startRefresh()
        else pullRefreshState.endRefresh()
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        
        ExpandableSearchBanner(
            query = state.query,
            onQueryChange = { onEvent(OverviewEvent.OnQueryChange(it)) },
            selectedCity = state.weatherInfo?.place?.city ?: "Search",
            onSearch = { onEvent(OverviewEvent.OnSearch(state.query)) },
            isActive = state.isSearchBarActive,
            onSearchBarClick = { onEvent(OverviewEvent.OnSearchBarClick) },
            onLocationButtonClick = { onEvent(OverviewEvent.OnLocationButtonClick) },
            onArrowBackClick = { onEvent(OverviewEvent.OnBannerCollapse) },
            onAddToSaved = { onEvent(OverviewEvent.OnAddToSaved(it)) },
            onNavigateToSettings = onNavigateToSettings,
            onSearchResultClick = { onEvent(OverviewEvent.OnSearchResultClick(it)) },
            onRemoveFromSaved = { onEvent(OverviewEvent.OnDeleteFromSaved(it)) },
            searchResults = state.searchResults,
            savedLocations = state.savedLocations,
            searchResultsError = state.searchResultsError,
            isSearchResultsLoading = state.isSearchResultsLoading
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(pullRefreshState.nestedScrollConnection)
        ) {
            
            Column(Modifier.fillMaxSize()) {
                
                AnimatedVisibility(
                    enter = fadeIn(),
                    exit = fadeOut(),
                    visible = state.weatherInfo != null
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                            .padding(16.dp)
                    ) {
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        state.weatherInfo?.current?.let {
                            SummaryBanner(it)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        
                        state.weatherInfo?.hourly?.let { HourlyBanner(it) }
                        
                        state.weatherInfo?.daily?.let { DailyBanner(it, onNavigateToDailyWeather) }
                        
                        state.weatherInfo?.current?.conditions?.let { ConditionsBanner(it) }
                        
                        state.weatherInfo?.airPollution?.let { AirPollutionBanner(it) }
                        
                        WeatherProviderButton(Modifier.align(Alignment.CenterHorizontally).padding(36.dp))
                    }
                }
            }
            
            PullToRefreshContainer(
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
            )
        }
    }
    
    BackHandler(enabled = state.isSearchBarActive) {
        onEvent(OverviewEvent.OnBannerCollapse)
    }
}
