package com.enjot.materialweather.ui.overviewscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.enjot.materialweather.domain.model.DailyWeather
import com.enjot.materialweather.ui.overviewscreen.air.AirPollutionBanner
import com.enjot.materialweather.ui.overviewscreen.conditions.ConditionsBanner
import com.enjot.materialweather.ui.overviewscreen.daily.DailyBanner
import com.enjot.materialweather.ui.overviewscreen.hourly.HourlyBanner
import com.enjot.materialweather.ui.overviewscreen.summary.SummaryBanner
import com.enjot.materialweather.ui.overviewscreen.search.ExpandableSearchBanner
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
                onQueryChange = {
                    onEvent(
                        OverviewEvent.SearchBanner.OnQueryChange(
                            it
                        )
                    )
                },
                selectedCity = state.weatherInfo?.place?.city
                    ?: "Search",
                onSearch = {
                    onEvent(
                        OverviewEvent.SearchBanner.OnSearch(
                            state.query
                        )
                    )
                },
                isActive = state.isSearchBarActive,
                onSearchBarClick = { onEvent(OverviewEvent.OnSearchBarClick) },
                onUseCurrentLocationClick = {
                    onEvent(OverviewEvent.SearchBanner.OnCurrentLocationButtonClick)
                },
                onArrowBackClick = { onEvent(OverviewEvent.SearchBanner.OnArrowBackClick) },
                onAddToFavorites = { result ->
                    onEvent(
                        OverviewEvent.SearchBanner.OnAddToFavorites(
                            result
                        )
                    )
                },
                onNavigateToSettings = onNavigateToSettings,
                onSearchResultClick = { result ->
                    onEvent(
                        OverviewEvent.SearchBanner.OnSearchResultClick(
                            result
                        )
                    )
                },
                searchResults = viewModel.state.searchResults,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            AnimatedVisibility(
                enter = fadeIn(),
                exit = fadeOut(),
                visible = !state.isLoading && state.weatherInfo != null
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
                    state.weatherInfo?.daily?.let {
                        DailyBanner(
                            it,
                            onNavigateToDailyWeather
                        )
                    }
                    state.weatherInfo?.current?.conditions?.let {
                        ConditionsBanner(
                            it
                        )
                    }
                    state.weatherInfo?.airPollution?.let {
                        AirPollutionBanner(
                            it
                        )
                    }
                    Text(
                        text = "openweathermap.org",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(24.dp)
                            .clip(CircleShape)
                            .clickable { }
                            .padding(16.dp)
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = state.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}