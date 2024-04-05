package com.enjot.materialweather.ui.settingsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.usecase.ManageWeatherUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val manageWeatherUpdateUseCase: ManageWeatherUpdateUseCase
) : ViewModel() {
    
    private var _state = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()
    
    init {
        viewModelScope.launch {
            manageWeatherUpdateUseCase.userPreferences.collect { userPreferences ->
                _state.update { it.copy(repeatInterval = userPreferences.backgroundUpdatesRepeatInterval) }
            }
        }
        isScheduledWork()
    }
    
    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnBackgroundWeatherUpdatesClick -> {
                isScheduledWork()
                if (state.value.isWorkScheduled) cancelWeatherUpdateWork()
                else scheduleWeatherUpdateWork()
                isScheduledWork()
            }
            
            is SettingsEvent.OnSetIntervals -> {
                cancelWeatherUpdateWork()
                viewModelScope.launch {
                    manageWeatherUpdateUseCase.updateRepeatInterval(event.minutes)
                    scheduleWeatherUpdateWork()
                }
            }
            
            is SettingsEvent.OnLocationBasedClick -> {
            
            }
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
