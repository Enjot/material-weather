package com.enjot.materialweather.presentation.overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.weatherInfo
    val air = state?.airPollution
    val scrollState = rememberScrollState()
    val refreshState = rememberPullRefreshState(
        refreshing = viewModel.state.isLoading,
        onRefresh = {
            coroutineScope.launch {
                focusManager.clearFocus()
                viewModel.getWeatherInfoForGpsLocation()
            }
        },
        refreshThreshold = 50.dp,
        refreshingOffset = 250.dp
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .pullRefresh(refreshState)
        ) {
            DockedSearchBar(
                query = viewModel.query,
                onQueryChange = { viewModel.query = it },
                onSearch = {
                    coroutineScope.launch {
                        focusManager.clearFocus()
                        viewModel.refreshWeatherInfo()
                    }
                },
                active = false,
                onActiveChange = {},
                trailingIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            focusManager.clearFocus()
                            viewModel.refreshWeatherInfo()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search"
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                content = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(
                visible = !viewModel.state.isLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .verticalScroll(scrollState)
                ) {
                    Text("Now")
                    if (state != null) {
                        state.searchResult?.let { Text(it.name) }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Row {
                                if (state != null) {
                                    Text(
                                        text = "${state.current?.temp}°",
                                        style = MaterialTheme.typography.displaySmall,
                                    )
                                }
                                if (state != null) {
                                    AsyncImage(
                                        model = "https://openweathermap.org/img/wn/${
                                            state.current?.icon
                                        }@2x.png",
                                        contentDescription = null,
                                        modifier = Modifier.size(75.dp)
                                    )
                                }
                            }
                            if (state != null) {
                                Text("Max: ${state.current?.maxTemp}° · Min: ${state.current?.minTemp}°")
                            }
                        }
                        Column {
                            if (state != null) {
                                Text("${state.current?.description}")
                            }
                            if (state != null) {
                                Text("Feels like ${state.current?.feelsLike}°")
                            }
                        }
                    }
                    if (state != null) {
                        if (state.hourly != null) {
                            Text(
                                text = "Hourly",
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                            LazyRow(Modifier.fillMaxWidth()) {
                                item {
                                    Spacer(modifier = Modifier.width(16.dp))
                                }
                                items(state.hourly) {
                                    Column(
                                        Modifier
                                            .padding(horizontal = 4.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                                            .padding(8.dp)
                                    ) {
                                        Text("${it.temp}°")
                                        AsyncImage(
                                            model = "https://openweathermap.org/img/wn/${
                                                it.icon
                                            }@2x.png",
                                            contentDescription = null
                                        )
                                        Text("${it.localTime}")
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.width(16.dp))
                                }
                            }
                        }
                    }
                    if (state != null) {
                        if (state.daily != null) {
                            Text(
                                text = "Daily",
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .wrapContentHeight()
                            ) {
                                state.daily.forEach {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(4.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                                            .padding(8.dp)
                                    ) {
                                        Text("${it.localDate}")
                                        AsyncImage(
                                            model = "https://openweathermap.org/img/wn/${
                                                it.icon
                                            }@2x.png",
                                            contentDescription = null
                                        )
                                        Text("${it.tempDay}°/${it.tempNight}°")
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                if (air != null) {
                                    Text(
                                        text = "Air",
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column {
                                            Text("aqi: ${air.aqi}")
                                            Text("")
                                            Text("nh3: ${air.nh3}")
                                            Text("co: ${air.co}")
                                            Text("no: ${air.no}")
                                        }
                                        Column {
                                            Text("no2: ${air.no2}")
                                            Text("o3: ${air.o3}")
                                            Text("so2: ${air.so2}")
                                            Text("pm25: ${air.pm25}")
                                            Text("pm10: ${air.pm10}")
                                        }
                                    }
                                }
                            }
                            Text(
                                text = "openweathermap.org",
                                modifier = Modifier.padding(32.dp)
                            )
                        }
                    }
                }
            }
            
            
        }
        PullRefreshIndicator(
            refreshing = viewModel.state.isLoading,
            state = refreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}

