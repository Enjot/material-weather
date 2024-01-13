package com.enjot.materialweather.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.data.remote.CurrentWeatherDto
import com.enjot.materialweather.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {
    
    var state by mutableStateOf(CurrentWeatherDto())
    var city by mutableStateOf("Sieniawa")
    init {
        viewModelScope.launch {
            state = weatherRepository.getCurrentWeather(city)
        }
    }
    
    fun getWeather() {
        viewModelScope.launch {
            state = weatherRepository.getCurrentWeather(city)
        }
    }
}