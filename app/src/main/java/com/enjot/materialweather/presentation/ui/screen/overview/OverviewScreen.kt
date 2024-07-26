package com.enjot.materialweather.presentation.ui.screen.overview

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enjot.materialweather.R
import com.enjot.materialweather.presentation.ui.core.WeatherProviderButton
import com.enjot.materialweather.presentation.ui.banner.airpollution.AirPollutionBanner
import com.enjot.materialweather.presentation.ui.banner.conditions.ConditionsBanner
import com.enjot.materialweather.presentation.ui.banner.dailyforecast.DailyBanner
import com.enjot.materialweather.presentation.ui.banner.hourlyforecast.HourlyBanner
import com.enjot.materialweather.presentation.ui.banner.summary.SummaryBanner
import com.enjot.materialweather.presentation.ui.screen.overview.search.ExpandableSearchBanner

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
    
    val pullRefreshState = rememberPullToRefreshState { weatherInfo.place != null }
    
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
            selectedCity = weatherInfo.place?.city ?: stringResource(R.string.search),
            onSearch = viewModel::search,
            isActive = state.isSearchBannerExpanded,
            onExpand = viewModel::expandSearchBanner,
            onLocationButtonClick = viewModel::getLocationBasedWeather,
            onArrowBackClick = viewModel::collapseSearchBanner,
            onAddToSaved = viewModel::save,
            onNavigateToSettings = onNavigateToSettings,
            onSearchResultClick = viewModel::chooseSearchResult,
            onRemoveFromSaved = viewModel::remove,
            searchState = state.searchState,
            savedLocations = savedLocations,
        )
        
        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(pullRefreshState.nestedScrollConnection)
        ) {
            
            Column(Modifier.fillMaxSize()) {
                
                if (state.weatherState is WeatherState.Error) {
                    Text(
                        text = (state.weatherState as WeatherState.Error).message.asString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(32.dp)
                            .padding(8.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(32.dp))
                }
                
                AnimatedContent(
                    targetState = state.weatherState is WeatherState.Loading,
                    label = ""
                ) { isLoading ->
                    if (isLoading) {
                        Box(Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 128.dp)
                            )
                        }
                    } else {
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
                                    .padding(horizontal = 16.dp)
                            ) {
                                
                                weatherInfo.current?.let {
                                    SummaryBanner(
                                        it.temp,
                                        it.description,
                                        it.feelsLike,
                                        it.localFormattedTime
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                                
                                weatherInfo.hourly?.let { HourlyBanner(it) }
                                
                                weatherInfo.daily?.let {
                                    DailyBanner(it, onNavigateToDailyWeather)
                                    ConditionsBanner(it[0])
                                }
                                
                                weatherInfo.airPollution?.let { AirPollutionBanner(it) }
                                
                                WeatherProviderButton(
                                    Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(36.dp)
                                )
                            }
                        }
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
    
    BackHandler(enabled = state.isSearchBannerExpanded) {
        viewModel.collapseSearchBanner()
    }
}
