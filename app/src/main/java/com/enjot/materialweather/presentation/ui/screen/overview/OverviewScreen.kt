package com.enjot.materialweather.presentation.ui.screen.overview

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.model.CurrentWeather
import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.presentation.ui.component.banner.airpollution.AirPollutionBanner
import com.enjot.materialweather.presentation.ui.component.banner.conditions.ConditionsBanner
import com.enjot.materialweather.presentation.ui.component.banner.dailyforecast.DailyBanner
import com.enjot.materialweather.presentation.ui.component.banner.hourlyforecast.HourlyBanner
import com.enjot.materialweather.presentation.ui.component.banner.summary.SummaryBanner
import com.enjot.materialweather.presentation.ui.core.WeatherProviderButton
import com.enjot.materialweather.presentation.ui.screen.overview.search.ExpandableSearchBanner
import com.enjot.materialweather.presentation.ui.theme.MaterialWeatherTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun OverviewScreenRoot(
    onNavigateToDailyWeather: (Int) -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: OverviewViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val weatherInfo by viewModel.weatherInfo.collectAsStateWithLifecycle()
    val savedLocations by viewModel.savedLocations.collectAsStateWithLifecycle()

    OverviewScreen(
        state = state,
        savedLocations = savedLocations,
        weatherInfo = weatherInfo,
        onDailyWeatherClick = onNavigateToDailyWeather,
        onSettingsClick = onNavigateToSettings,
        onRefresh = viewModel::pullRefresh,
        onQueryChange = viewModel::updateQuery,
        onSearch = viewModel::search,
        onExpandSearchBanner = viewModel::expandSearchBanner,
        onLocationButtonClick = viewModel::getLocationBasedWeather,
        onArrowBackClick = viewModel::collapseSearchBanner,
        onSave = viewModel::save,
        onRemove = viewModel::remove,
        onSearchResultClick = viewModel::chooseSearchResult
    )

    BackHandler(enabled = state.isSearchBannerExpanded) {
        viewModel.collapseSearchBanner()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    state: OverviewUiState,
    savedLocations: List<SavedLocation>,
    weatherInfo: WeatherInfo,
    onRefresh: () -> Unit,
    onDailyWeatherClick: (Int) -> Unit,
    onSettingsClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onExpandSearchBanner: () -> Unit,
    onLocationButtonClick: () -> Unit,
    onArrowBackClick: () -> Unit,
    onSave: (SearchResult) -> Unit,
    onRemove: (SavedLocation) -> Unit,
    onSearchResultClick: (SearchResult) -> Unit
) {
    val density = LocalDensity.current
    val scrollState = rememberScrollState()
    val pullToRefreshState = rememberPullToRefreshState()
    var searchBannerHeightPx by rememberSaveable { mutableIntStateOf(0) }
    val columnSpacerHeight by animateDpAsState(
        targetValue = with(density) { searchBannerHeightPx.toDp() },
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )
    val surfaceColor = MaterialTheme.colorScheme.surface

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        AnimatedContent(
            targetState = state.weatherState is WeatherState.Loading,
            label = "",
        ) { isLoading ->
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateEnterExit(
                            enter = slideInVertically { it },
                            exit = slideOutVertically { it }
                        )
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = columnSpacerHeight + 64.dp)
                    )
                }
            } else {
                AnimatedVisibility(
                    enter = EnterTransition.None,
                    exit = ExitTransition.None,
                    visible = weatherInfo.place != null,
                    modifier = Modifier
                        .animateEnterExit(
                            enter = slideInVertically { it },
                            exit = slideOutVertically { it }
                        )
                ) {
                    PullToRefreshBox(
                        isRefreshing = state.weatherState is WeatherState.Refreshing,
                        onRefresh = onRefresh,
                        state = pullToRefreshState,
                        indicator = {
                            Indicator(
                                isRefreshing = state.weatherState is WeatherState.Refreshing,
                                state = pullToRefreshState,
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .offset {
                                        IntOffset(
                                            0,
                                            (columnSpacerHeight - 24.dp).roundToPx()
                                        )
                                    }
                                    .graphicsLayer {
                                        scaleX = pullToRefreshState.distanceFraction.coerceAtMost(1.5f)
                                        scaleY = pullToRefreshState.distanceFraction.coerceAtMost(1.5f)
                                    }
                            )
                        }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawWithContent {
                                    drawContent()
                                    drawRect(
                                        brush = Brush.verticalGradient(
                                            0.0f to surfaceColor,
                                            0.7f to surfaceColor,
                                            1f to Color.Transparent,
                                            startY = 0f,
                                            endY = (columnSpacerHeight + 32.dp).toPx()
                                        ),
                                        size = Size(
                                            this.size.width,
                                            (columnSpacerHeight + 32.dp).toPx()
                                        )
                                    )
                                }
                                .padding(horizontal = 16.dp)
                                .verticalScroll(scrollState)
                        ) {
                            Spacer(
                                Modifier
                                    .statusBarsPadding()
                                    .height(columnSpacerHeight)
                            )

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
                                DailyBanner(it, onDailyWeatherClick)
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    with(density) { searchBannerHeightPx = it.size.height }
                }
        ) {
            ExpandableSearchBanner(
                query = state.query,
                onQueryChange = onQueryChange,
                selectedCity = weatherInfo.place?.city ?: stringResource(R.string.search),
                onSearch = onSearch,
                isActive = state.isSearchBannerExpanded,
                onExpand = onExpandSearchBanner,
                onLocationButtonClick = onLocationButtonClick,
                onArrowBackClick = onArrowBackClick,
                onAddToSaved = onSave,
                onNavigateToSettings = onSettingsClick,
                onSearchResultClick = onSearchResultClick,
                onRemoveFromSaved = onRemove,
                searchState = state.searchState,
                savedLocations = savedLocations,
                modifier = Modifier
            )

            AnimatedVisibility(
                visible = state.weatherState is WeatherState.Error,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            ) {
                if (state.weatherState is WeatherState.Error) {
                    Text(
                        text = state.weatherState.message.asString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OverviewScreenPreview() {
    var state by remember {
        mutableStateOf(
            OverviewUiState(
                weatherState = WeatherState.Idle
            )
        )
    }
    val weatherInfo = WeatherInfo(
        current = CurrentWeather(
            localFormattedTime = "sententiae",
            temp = 1402,
            minTemp = 8512,
            maxTemp = 1857,
            feelsLike = 3306,
            clouds = 9816,
            windGust = 0.1,
            rainPrecipitation = 2.3,
            snowPrecipitation = 4.5,
            weather = "facilis",
            description = "ius",
            icon = "fastidii",
            conditions = CurrentWeather.WeatherConditions(
                sunrise = "eius",
                sunset = "dapibus",
                windSpeed = 7119,
                windDeg = 6249,
                humidity = 5400,
                dewPoint = 7473,
                pressure = 5193,
                uvi = 4931

            )
        )
    )
    val savedLocations = emptyList<SavedLocation>()
    MaterialWeatherTheme {
        OverviewScreen(
            state = state,
            savedLocations = savedLocations,
            weatherInfo = weatherInfo,
            onRefresh = { },
            onDailyWeatherClick = { },
            onSettingsClick = { },
            onQueryChange = { state = state.copy(query = it) },
            onSearch = { },
            onExpandSearchBanner = { state = state.copy(isSearchBannerExpanded = true) },
            onLocationButtonClick = { },
            onArrowBackClick = { state = state.copy(isSearchBannerExpanded = false) },
            onSave = { },
            onRemove = { },
            onSearchResultClick = { }
        )
    }
}