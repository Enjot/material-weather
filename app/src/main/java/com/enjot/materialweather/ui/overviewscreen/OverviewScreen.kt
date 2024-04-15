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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val state by viewModel.state.collectAsStateWithLifecycle()
    val weatherInfo by viewModel.weatherInfo.collectAsStateWithLifecycle()
    val savedLocations by viewModel.savedLocations.collectAsStateWithLifecycle()
    
    val scrollState = rememberScrollState()
    
    val pullRefreshState = rememberPullToRefreshState()
    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            weatherInfo.place?.let { viewModel.pullRefresh() }
            pullRefreshState.endRefresh()
        }
    }
    
    val scaleFraction = if (pullRefreshState.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullRefreshState.progress).coerceIn(0f, 1f)
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        
        ExpandableSearchBanner(
            query = state.query,
            onQueryChange = viewModel::updateQuery,
            selectedCity = weatherInfo.place?.city ?: "Search",
            onSearch = viewModel::search,
            isActive = state.isSearchBarActive,
            onSearchBarClick = viewModel::expandSearchBar,
            onLocationButtonClick = viewModel::getLocationBasedWeather,
            onArrowBackClick = viewModel::collapseSearchBar,
            onAddToSaved = viewModel::save,
            onNavigateToSettings = onNavigateToSettings,
            onSearchResultClick = viewModel::chooseSearchResult,
            onRemoveFromSaved = viewModel::remove,
            searchResults = state.searchResults,
            savedLocations = savedLocations,
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
                
                if (state.weatherError) {
                    Text(
                        text = "Failed to load new weather data",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                    )
                }
                
                AnimatedVisibility(
                    enter = fadeIn(),
                    exit = fadeOut(),
                    visible = weatherInfo.place != null
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                            .padding(16.dp)
                    ) {
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        weatherInfo.current?.let {
                            SummaryBanner(it)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        
                        weatherInfo.hourly?.let { HourlyBanner(it) }
                        
                        weatherInfo.daily?.let { DailyBanner(it, onNavigateToDailyWeather) }
                        
                        weatherInfo.current?.conditions?.let { ConditionsBanner(it) }
                        
                        weatherInfo.airPollution?.let { AirPollutionBanner(it) }
                        
                        WeatherProviderButton(
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(36.dp))
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
        viewModel.collapseSearchBar()
    }
}
