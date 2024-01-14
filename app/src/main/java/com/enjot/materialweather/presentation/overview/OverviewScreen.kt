package com.enjot.materialweather.presentation.overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state
    val air = state.airPollution
    val scrollState = rememberScrollState()
    var isRefreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                focusManager.clearFocus()
                isRefreshing = true
                viewModel.getWeatherInfo()
                delay(1000)
                isRefreshing = false
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
                        isRefreshing = true
                        viewModel.getWeatherInfo()
                        delay(1000)
                        isRefreshing = false
                    }
                },
                active = false,
                onActiveChange = {},
                trailingIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            focusManager.clearFocus()
                            isRefreshing = true
                            viewModel.getWeatherInfo()
                            delay(1000)
                            isRefreshing = false
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search"
                        )
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                content = {}
            )
            AnimatedVisibility(
                visible = !isRefreshing,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(4.dp)
                        .verticalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${
                            state.current?.icon
                        }@2x.png",
                        contentDescription = null,
                        modifier = Modifier.size(75.dp)
                    )
                    if (state.current != null) {
                        Text("temp: ${state.current.temp}")
                        Text("feelsLike: ${state.current.feelsLike}")
                        Text("description: ${state.current.description}")
                        Text("description: ${state.current.description}")
                        Text("description: ${state.current.description}")
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    if (air != null) {
                        Text("Air")
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text("aqi: ${air.aqi}")
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
                    Text(
                        text = "openweathermap.org",
                        modifier = Modifier.padding(32.dp)
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = refreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}
