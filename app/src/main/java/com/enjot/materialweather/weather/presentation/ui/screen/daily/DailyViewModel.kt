package com.enjot.materialweather.weather.presentation.ui.screen.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.weather.domain.model.WeatherInfo
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class DailyViewModel(
    localRepository: LocalRepository
) : ViewModel() {

    val state: StateFlow<WeatherInfo> = localRepository.getLocalWeather()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WeatherInfo()
        )
}