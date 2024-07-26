package com.enjot.materialweather.presentation.ui.screen.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.usecase.weather.LocalWeatherFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    localWeatherFlow: LocalWeatherFlow
) : ViewModel() {
    
    val state: StateFlow<WeatherInfo> = localWeatherFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WeatherInfo()
        )
}