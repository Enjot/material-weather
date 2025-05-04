package com.enjot.materialweather.weather.presentation.ui.screen.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.core.domain.utils.Resource
import com.enjot.materialweather.core.presentation.ui.R
import com.enjot.materialweather.core.presentation.ui.UiText
import com.enjot.materialweather.core.presentation.ui.toUiText
import com.enjot.materialweather.weather.domain.mapper.toSavedLocation
import com.enjot.materialweather.weather.domain.model.SavedLocation
import com.enjot.materialweather.weather.domain.model.SearchResult
import com.enjot.materialweather.weather.domain.model.WeatherInfo
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import com.enjot.materialweather.weather.domain.repository.RemoteRepository
import com.enjot.materialweather.weather.domain.usecase.GetWeatherFromLocationUseCase
import com.enjot.materialweather.weather.domain.usecase.UpdateWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class OverviewViewModel(
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val getWeatherFromLocationUseCase: GetWeatherFromLocationUseCase,
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    val weatherInfo: StateFlow<WeatherInfo> = localRepository.getLocalWeather()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WeatherInfo()
        )

    val savedLocations: StateFlow<List<SavedLocation>> = localRepository.getSavedLocations()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private var _state = MutableStateFlow(OverviewUiState())
    val state: StateFlow<OverviewUiState>
        get() = _state

    fun pullRefresh() {
        if (weatherInfo.value.place?.coordinates == null) {
            _state.update { it.copy(weatherState = WeatherState.Idle) }
            return
        }
        _state.update { it.copy(weatherState = WeatherState.Refreshing) }
        processWeatherLoading(weatherInfo.value.place?.coordinates!!)
    }

    fun chooseSearchResult(searchResult: SearchResult) {
        _state.update {
            it.copy(
                weatherState = WeatherState.Loading,
                searchState = SearchState.Idle(),
                query = "",
                isSearchBannerExpanded = false
            )
        }
        processWeatherLoading(searchResult.coordinates)
    }

    private fun processWeatherLoading(coordinates: Coordinates) {
        viewModelScope.launch {
            when (val resource = updateWeatherUseCase(coordinates)) {
                is Resource.Success -> _state.update { it.copy(weatherState = WeatherState.Idle) }

                is Resource.Error -> _state.update {
                    it.copy(
                        weatherState = WeatherState.Error(
                            resource.errorType?.toUiText()
                                ?: UiText.StringResource(R.string.unknown_error)
                        )
                    )
                }
            }
        }
    }

    fun search() {
        _state.update { it.copy(searchState = SearchState.Loading) }
        viewModelScope.launch {
            when (val resource = remoteRepository.getSearchResults(_state.value.query)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            searchState = SearchState.Idle(resource.data ?: emptyList())
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            searchState = SearchState.Error(
                                resource.errorType?.toUiText()
                                    ?: UiText.StringResource(R.string.unknown_error)
                            ),
                        )
                    }
                }
            }
        }
    }

    fun updateQuery(query: String) = _state.update { it.copy(query = query) }

    fun save(searchResult: SearchResult) =
        viewModelScope.launch { localRepository.addSavedLocation(searchResult.toSavedLocation()) }

    fun remove(savedLocation: SavedLocation) =
        viewModelScope.launch { localRepository.deleteSavedLocation(savedLocation) }

    fun getLocationBasedWeather() {
        _state.update {
            it.copy(
                isSearchBannerExpanded = false,
                weatherState = WeatherState.Loading,
                searchState = SearchState.Idle(),
                query = "",
            )
        }
        viewModelScope.launch {
            when (val resource = getWeatherFromLocationUseCase()) {
                is Resource.Success -> _state.update { it.copy(weatherState = WeatherState.Idle) }
                is Resource.Error -> _state.update {
                    it.copy(
                        weatherState = WeatherState.Error(
                            resource.errorType?.toUiText()
                                ?: UiText.StringResource(R.string.unknown_error)
                        )
                    )
                }
            }
        }
    }

    fun expandSearchBanner() = _state.update {
        it.copy(
            isSearchBannerExpanded = true,
            weatherState = WeatherState.Idle
        )
    }

    fun collapseSearchBanner() = _state.update {
        it.copy(
            isSearchBannerExpanded = false,
            searchState = SearchState.Idle(),
            query = ""
        )
    }
}

