package com.enjot.materialweather.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.location.LocationProvider
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.repository.WeatherRepository
import com.enjot.materialweather.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {
    
    var state by mutableStateOf(OverviewState())
        private set
    
    var query by mutableStateOf("Sieniawa")
    
    fun getWeatherInfo(searchResult: SearchResult) {
        viewModelScope.launch {
            val result =
                weatherRepository.getWeatherInfo(searchResult.coordinates)
            if (result.data != null) {
                state = state.copy(
                    weatherInfo = result.data
                )
            }
        }
    }
    
    fun refreshWeatherInfo() {
        viewModelScope.launch {
            val result = state.weatherInfo?.searchResult?.let {
                weatherRepository.getWeatherInfo(
                    it.coordinates
                )
            }
            if (result != null) {
                if (result.data != null) {
                    state = state.copy(
                        weatherInfo = result.data
                    )
                }
            }
        }
    }
    
    fun getWeatherInfoForGpsLocation() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationProvider.getCurrentLocation()?.let { coordinates ->
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
        }
    }
    
}