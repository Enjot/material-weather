package com.enjot.materialweather.ui.overviewscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.location.LocationTracker
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.repository.WeatherRepository
import com.enjot.materialweather.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    
    var state by mutableStateOf(OverviewUiState())
        private set
    
    fun onEvent(event: OverviewEvent) {
        when (event) {
            
            is OverviewEvent.OnSearchBarClick -> {
                state = state.copy(
                    isSearchBarActive = true
                )
            }
            
            is OverviewEvent.SearchBanner.OnQueryChange -> {
                state = state.copy(
                    query = event.query
                )
            }
            
            is OverviewEvent.SearchBanner.OnSearch -> {
                viewModelScope.launch {
                    getSearchResults(state.query)
                }
            }
            
            is OverviewEvent.SearchBanner.OnAddToFavorites -> {
            
            }
            
            is OverviewEvent.SearchBanner.OnCurrentLocationButtonClick -> {
                getWeatherInfoForGpsLocation()
            }
            
            is OverviewEvent.SearchBanner.OnArrowBackClick -> {
                state = state.copy(
                    isSearchBarActive = false,
                    searchResults = emptyList(),
                    query = ""
                )
            }
            
            is OverviewEvent.SearchBanner.OnSearchResultClick -> {
                viewModelScope.launch {
                    getWeatherInfo(event.searchResult)
                }
            }
            
            is OverviewEvent.OnPullRefresh -> {
                refreshWeatherInfo()
            }
            
            is OverviewEvent.OnSnackbarSettingsClick -> {
            
            }
            
            is OverviewEvent.OnWeatherProviderClick -> {
            
            }
            
            is OverviewEvent.OnDailyCardClick -> {
            
            }
        }
    }
    
    private fun getWeatherInfo(searchResult: SearchResult) {
        state = state.copy(
            isSearchBarActive = false,
            isLoading = true,
            searchResults = emptyList(),
            query = ""
        )
        viewModelScope.launch {
            val result =
                weatherRepository.getWeatherInfo(searchResult.coordinates)
            if (result.data != null) {
                state = state.copy(
                    weatherInfo = result.data,
                    isLoading = false
                )
            }
        }
    }
    
    private fun getSearchResults(query: String) {
        viewModelScope.launch {
            val searchResults = weatherRepository.getSearchResults(query)
            state = if (searchResults.data != null) {
                state.copy(
                    searchResults = searchResults.data
                )
            } else {
                state.copy(
                    searchResults = emptyList()
                )
            }
            
        }
        
    }
    
    private fun refreshWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            val result = state.weatherInfo?.searchResult?.let {
                weatherRepository.getWeatherInfo(
                    it.coordinates
                )
            }
            if (result != null) {
                if (result.data != null) {
                    state = state.copy(
                        weatherInfo = result.data,
                        isLoading = false
                    )
                }
            }
        }
    }
    
    private fun getWeatherInfoForGpsLocation() {
        viewModelScope.launch {
            state = state.copy(
                isSearchBarActive = false,
                isLoading = true,
                error = null,
                query = ""
            )
            locationTracker.getCurrentLocation()?.let { coordinates ->
                when (val result =
                    weatherRepository.getWeatherInfo(coordinates)) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
            addCloseable { state = state.copy(isLoading = false) }
        }
    }
    
}