package com.enjot.materialweather.ui.overviewscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.usecase.AddSavedLocationUseCase
import com.enjot.materialweather.domain.usecase.DeleteSavedLocationUseCase
import com.enjot.materialweather.domain.usecase.GetLocalWeatherUseCase
import com.enjot.materialweather.domain.usecase.GetSavedLocationsUseCase
import com.enjot.materialweather.domain.usecase.GetSearchResultsUseCase
import com.enjot.materialweather.domain.usecase.GetWeatherFromLocationUseCase
import com.enjot.materialweather.domain.usecase.UpdateWeatherUseCase
import com.enjot.materialweather.domain.utils.Resource
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
        _state.update { it.copy(isWeatherLoading = true, weatherError = false) }
        if (weatherInfo.value.place?.coordinates == null) {
            _state.update { it.copy(isWeatherLoading = false, weatherError = true) }
            return
        } else {
            val successfulUpdate = updateWeatherUseCase(weatherInfo.value.place?.coordinates!!)
            if (successfulUpdate) {
                _state.update { it.copy(isWeatherLoading = false, weatherError = false) }
            } else {
                _state.update { it.copy(isWeatherLoading = false, weatherError = true) }
            }
        }
    }
    
    fun search() {
        _state.update { it.copy(isSearchResultsLoading = true, searchResultsError = false) }
        viewModelScope.launch {
            when (val resource = getSearchResultsUseCase(_state.value.query)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            searchResults = resource.data ?: emptyList(),
                            isSearchResultsLoading = false
                        )
                    }
                }
                
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            searchResults = emptyList(),
                            isSearchResultsLoading = false,
                            searchResultsError = true
                        )
                    }
                }
            }
        }
    }
    
    fun updateQuery(query: String) = _state.update { it.copy(query = query) }
    
    fun expandSearchBar() = _state.update { it.copy(isSearchBarActive = true) }
    
    fun save(searchResult: SearchResult) = viewModelScope.launch { addSavedLocationUseCase(searchResult) }
    
    fun remove(savedLocation: SavedLocation) = viewModelScope.launch { deleteSavedLocationUseCase(savedLocation) }
    
    fun chooseSearchResult(searchResult: SearchResult) {
        _state.update {
            it.copy(
                isWeatherLoading = true,
                weatherError = false,
                isSearchBarActive = false,
                query = "",
                searchResults = emptyList(),
            )
        }
        viewModelScope.launch {
            val isSuccess = updateWeatherUseCase(searchResult.coordinates)
            _state.update { it.copy(isWeatherLoading = false, weatherError = !isSuccess) }
        }
    }
    
    fun getLocationBasedWeather() {
        _state.update {
            it.copy(
                isSearchBarActive = false,
                searchResultsError = false,
                isWeatherLoading = true,
                weatherError = false,
                searchResults = emptyList(),
                query = ""
            )
        }
        // TODO handle success/failure
        viewModelScope.launch {
            getWeatherFromLocationUseCase()
            _state.update { it.copy(isWeatherLoading = false) }
        }
    }
    
    fun collapseSearchBar() {
        _state.update {
            it.copy(
                isSearchBarActive = false,
                searchResultsError = false,
                searchResults = emptyList(),
                query = ""
            )
        }
    }
}
