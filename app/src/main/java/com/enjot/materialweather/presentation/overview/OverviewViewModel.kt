package com.enjot.materialweather.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.location.LocationTracker
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {
    
    var state by mutableStateOf(WeatherInfo())
        private set
    
    var query by mutableStateOf("Sieniawa")
    init {
        viewModelScope.launch {
            val result = weatherRepository.getWeatherInfo(query)
            state = state.copy(
                current = result.data?.current,
                airPollution = result.data?.airPollution
            )
        }
    }
    
    fun getWeatherInfo() {
        viewModelScope.launch {
                val result = weatherRepository.getWeatherInfo(query)
            state = state.copy(
                current = result.data?.current,
                airPollution = result.data?.airPollution
            )
        }
    }
}