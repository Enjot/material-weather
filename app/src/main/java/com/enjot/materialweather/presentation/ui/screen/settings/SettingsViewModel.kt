package com.enjot.materialweather.presentation.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.model.UserPreferences
import com.enjot.materialweather.domain.usecase.settings.ManageWeatherUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
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
        setUpdateWeatherWorkState()
    }

    fun scheduleBackgroundWeather() {
        if (state.value.isWorkScheduled) manageWeatherUpdateUseCase.cancel()
        else runBlocking { manageWeatherUpdateUseCase.schedule() }
        setUpdateWeatherWorkState()
    }

    fun setIntervals(minutes: Long) {
        runBlocking { manageWeatherUpdateUseCase.updateRepeatInterval(minutes) }
        if (state.value.isWorkScheduled) {
            manageWeatherUpdateUseCase.cancel()
            runBlocking {
                manageWeatherUpdateUseCase.schedule()
            }
            setUpdateWeatherWorkState()
        }
    }

    private fun setUpdateWeatherWorkState() {
        runBlocking {
            val isWorkScheduled = manageWeatherUpdateUseCase.isWorkScheduled()
            _state.update { it.copy(isWorkScheduled = isWorkScheduled) }
        }
    }
}