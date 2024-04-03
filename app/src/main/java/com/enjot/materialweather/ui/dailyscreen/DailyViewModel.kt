package com.enjot.materialweather.ui.dailyscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.usecase.GetLocalWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    localLocalWeatherUseCase: GetLocalWeatherUseCase
) : ViewModel() {
    
    private var _state = MutableStateFlow(WeatherInfo())
    val state: StateFlow<WeatherInfo> = _state.asStateFlow()
    
    init {
        viewModelScope.launch {
            localLocalWeatherUseCase().collect { weatherInfo ->
                _state.update { it.copy(
                    place = weatherInfo?.place,
                    current = weatherInfo?.current,
                    hourly = weatherInfo?.hourly,
                    daily = weatherInfo?.daily,
                    airPollution = weatherInfo?.airPollution,
                ) }
            }
        }
    }
}