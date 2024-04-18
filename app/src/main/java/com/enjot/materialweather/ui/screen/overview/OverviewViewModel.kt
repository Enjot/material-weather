package com.enjot.materialweather.ui.screen.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.usecase.savedlocation.AddSavedLocationUseCase
import com.enjot.materialweather.domain.usecase.savedlocation.DeleteSavedLocationUseCase
import com.enjot.materialweather.domain.usecase.weather.GetLocalWeatherUseCase
import com.enjot.materialweather.domain.usecase.savedlocation.GetSavedLocationsUseCase
import com.enjot.materialweather.domain.usecase.search.GetSearchResultsUseCase
import com.enjot.materialweather.domain.usecase.weather.GetWeatherFromLocationUseCase
import com.enjot.materialweather.domain.usecase.weather.UpdateWeatherUseCase
import com.enjot.materialweather.domain.utils.Resource
import com.enjot.materialweather.ui.utils.UiText
import com.enjot.materialweather.ui.utils.toUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OverviewViewModel @Inject constructor(
    getLocalWeatherUseCase: GetLocalWeatherUseCase,
    getSavedLocationsUseCase: GetSavedLocationsUseCase,
    private val getSearchResultsUseCase: GetSearchResultsUseCase,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val addSavedLocationUseCase: AddSavedLocationUseCase,
    private val deleteSavedLocationUseCase: DeleteSavedLocationUseCase,
    private val getWeatherFromLocationUseCase: GetWeatherFromLocationUseCase
) : ViewModel() {
    
    val weatherInfo: StateFlow<WeatherInfo> = getLocalWeatherUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WeatherInfo()
        )
    
    val savedLocations: StateFlow<List<SavedLocation>> = getSavedLocationsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )
    
    private var _state = MutableStateFlow(OverviewUiState())
    val state: StateFlow<OverviewUiState>
        get() = _state
    
    suspend fun pullRefresh() {
        if (weatherInfo.value.place?.coordinates == null) {
            _state.update { it.copy(weatherState = WeatherState.Idle) }
            return
        }
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
        viewModelScope.launch { processWeatherLoading(searchResult.coordinates) }
    }
    
    private suspend fun processWeatherLoading(coordinates: Coordinates) {
        when (val resource = updateWeatherUseCase(coordinates)) {
            is Resource.Success -> _state.update { it.copy(weatherState = WeatherState.Idle) }
            
            is Resource.Error -> _state.update {
                it.copy(
                    weatherState = WeatherState.Error(
                        resource.errorType?.toUiText() ?: UiText.StringResource(R.string.unknown_error)
                    )
                )
            }
        }
    }
    
    fun search() {
        _state.update { it.copy(searchState = SearchState.Loading) }
        viewModelScope.launch {
            when (val resource = getSearchResultsUseCase(_state.value.query)) {
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
                                resource.errorType?.toUiText() ?: UiText.StringResource(R.string.unknown_error)
                            ),
                        )
                    }
                }
            }
        }
    }
    
    fun updateQuery(query: String) = _state.update { it.copy(query = query) }
    
    fun save(searchResult: SearchResult) =
        viewModelScope.launch { addSavedLocationUseCase(searchResult) }
    
    fun remove(savedLocation: SavedLocation) =
        viewModelScope.launch { deleteSavedLocationUseCase(savedLocation) }
    
    
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
                            resource.errorType?.toUiText() ?: UiText.StringResource(R.string.unknown_error)
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

