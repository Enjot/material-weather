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
import androidx.compose.foundation.layout.systemBarsPadding
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
fun WeatherOverview(
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state
    val scrollState = rememberScrollState()
    var isRefreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                viewModel.getWeather()
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
                query = viewModel.city,
                onQueryChange = { viewModel.city = it },
                onSearch = { viewModel.getWeather() },
                active = false,
                onActiveChange = {},
                trailingIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            isRefreshing = true
                            viewModel.getWeather()
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
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
            
            }
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
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("lat: ${state.coord?.lat}")
                        Text("lon: ${state.coord?.lon}")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("base: ${state.base}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("weather id: ${state.weather?.get(0)?.id}")
                    Text("weather main: ${state.weather?.get(0)?.main}")
                    Text("weather description: ${state.weather?.get(0)?.description}")
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${state.weather?.get(0)?.icon}@2x.png",
                        contentDescription = null,
                        modifier = Modifier.size(75.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("visibility: ${state.visibility}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text("main")
                            Text("temp: ${state.main?.temp}")
                            Text("feelsLike: ${state.main?.temp}")
                            Text("pressure: ${state.main?.pressure}")
                            Text("humidity: ${state.main?.humidity}")
                            Text("tempMin: ${state.main?.tempMin}")
                            Text("tempMax: ${state.main?.tempMax}")
                            Text("seaLevel: ${state.main?.seaLevel}")
                            Text("grndLevel: ${state.main?.grndLevel}")
                            Text("")
                            Text("clouds all: ${state.clouds?.all}")
                        }
                        Column {
                            Text("wind")
                            Text("speed: ${state.wind?.speed}")
                            Text("deg: ${state.wind?.deg}")
                            Text("gust: ${state.wind?.gust}")
                            Text("")
                            Text("rain")
                            Text("lastHour: ${state.rain?.lastHour}")
                            Text("lastThreeHour: ${state.rain?.lastThreeHours}")
                            Text("")
                            Text("snow")
                            Text("lastHour: ${state.snow?.lastHour}")
                            Text("lastThreeHours: ${state.snow?.lastThreeHours}")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text("dt: ${state.dt}")
                            Text("timezone: ${state.timezone}")
                            Text("id: ${state.id}")
                            Text("name: ${state.name}")
                            Text("cod: ${state.cod}")
                        }
                        Column {
                            Text("sys")
                            Text("type: ${state.sys?.type}")
                            Text("id: ${state.sys?.id}")
                            Text("message: ${state.sys?.message}")
                            Text("country: ${state.sys?.country}")
                            Text("sunrise: ${state.sys?.sunrise}")
                            Text("sunset: ${state.sys?.sunset}")
                        }
                    }
                    Spacer(modifier = Modifier.systemBarsPadding())
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

