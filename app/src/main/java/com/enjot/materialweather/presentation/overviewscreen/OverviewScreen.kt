package com.enjot.materialweather.presentation.overviewscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.enjot.materialweather.presentation.overviewscreen.components.AirPollutionBanner
import com.enjot.materialweather.presentation.overviewscreen.components.ConditionsBanner
import com.enjot.materialweather.presentation.overviewscreen.components.DailyBanner
import com.enjot.materialweather.presentation.overviewscreen.components.HourlyBanner
import com.enjot.materialweather.presentation.overviewscreen.components.SummaryBanner
import com.enjot.materialweather.presentation.overviewscreen.searchbanner.ExpandableSearchBanner
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scrollState = rememberScrollState()
    val onEvent = viewModel::onEvent
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.state.isLoading,
        onRefresh = {
                viewModel.onEvent(OverviewEvent.OnPullRefresh)
        },
        refreshThreshold = 50.dp,
        refreshingOffset = 200.dp
    )
    Box {
        Column(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
        ) {
            ExpandableSearchBanner(
                query = state.query,
                onQueryChange = { onEvent(OverviewEvent.SearchBanner.OnQueryChange(it)) },
                selectedCity = state.weatherInfo?.searchResult?.city ?: "Search",
                onSearch = { onEvent(OverviewEvent.SearchBanner.OnSearch(state.query))},
                isActive = state.isSearchBarActive,
                onSearchBarClick = { onEvent(OverviewEvent.OnSearchBarClick) },
                onLocationIconClick = { onEvent(OverviewEvent.SearchBanner.OnLocationIconClick) },
                onArrowBackClick = { onEvent(OverviewEvent.SearchBanner.OnArrowBackClick) },
                searchResults = viewModel.state.searchResults
            )
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                state.weatherInfo?.current?.let { SummaryBanner(it) }
                state.weatherInfo?.hourly?.let { HourlyBanner(it) }
                state.weatherInfo?.daily?.let { DailyBanner(it) }
                state.weatherInfo?.current?.conditions?.let {
                    ConditionsBanner(
                        it
                    )
                }
                state.weatherInfo?.airPollution?.let { AirPollutionBanner(it) }
                if (!state.isLoading) {
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
            refreshing = viewModel.state.isLoading,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
    
}