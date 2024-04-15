package com.enjot.materialweather.ui.settingsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.model.UserPreferences
import com.enjot.materialweather.domain.usecase.ManageWeatherUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val manageWeatherUpdateUseCase: ManageWeatherUpdateUseCase
) : ViewModel() {
    
    val userPreferences: StateFlow<UserPreferences> = manageWeatherUpdateUseCase.userPreferences
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = UserPreferences(60)
        )
    
    private var _state = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()
    
    init {
        isScheduledWork()
    }
    
    fun scheduleBackgroundWeather() {
        isScheduledWork()
        if (state.value.isWorkScheduled) cancelWeatherUpdateWork()
        else scheduleWeatherUpdateWork()
        isScheduledWork()
    }
    
    fun setIntervals(minutes: Long) {
        cancelWeatherUpdateWork()
        viewModelScope.launch {
            manageWeatherUpdateUseCase.updateRepeatInterval(minutes)
            scheduleWeatherUpdateWork()
        }
    }
    
    private fun scheduleWeatherUpdateWork() {
        viewModelScope.launch {
            manageWeatherUpdateUseCase.schedule()
        }
    }
    
    private fun cancelWeatherUpdateWork() {
        manageWeatherUpdateUseCase.cancel("UpdateWeatherWork")
    }
    
    private fun isScheduledWork() {
        viewModelScope.launch {
            val isWorkScheduled = manageWeatherUpdateUseCase.isWorkScheduled("UpdateWeatherWork")
            _state.update { it.copy(isWorkScheduled = isWorkScheduled) }
        }
    }
}
