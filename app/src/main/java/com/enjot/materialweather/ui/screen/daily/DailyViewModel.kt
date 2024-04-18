package com.enjot.materialweather.ui.screen.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.usecase.weather.GetLocalWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    getLocalWeatherUseCase: GetLocalWeatherUseCase
) : ViewModel() {
    
    val state: StateFlow<WeatherInfo> = getLocalWeatherUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WeatherInfo()
        )
    
}