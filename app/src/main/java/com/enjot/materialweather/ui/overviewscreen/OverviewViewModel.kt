package com.enjot.materialweather.ui.overviewscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjot.materialweather.domain.usecase.AddSavedLocationUseCase
import com.enjot.materialweather.domain.usecase.DeleteSavedLocationUseCase
import com.enjot.materialweather.domain.usecase.FetchAndStoreWeatherInfoUseCase
import com.enjot.materialweather.domain.usecase.GetLocalWeatherUseCase
import com.enjot.materialweather.domain.usecase.GetSavedLocationsUseCase
import com.enjot.materialweather.domain.usecase.GetSearchResultsUseCase
import com.enjot.materialweather.domain.usecase.GetWeatherFromLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val getLocalWeatherUseCase: GetLocalWeatherUseCase,
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase,
    private val getSearchResultsUseCase: GetSearchResultsUseCase,
    private val fetchAndStoreWeatherInfoUseCase: FetchAndStoreWeatherInfoUseCase,
    private val addSavedLocationUseCase: AddSavedLocationUseCase,
    private val deleteSavedLocationUseCase: DeleteSavedLocationUseCase,
    private val getWeatherFromLocationUseCase: GetWeatherFromLocationUseCase
) : ViewModel() {
    
    private var _state = MutableStateFlow(OverviewUiState())
    val state: StateFlow<OverviewUiState> = _state.asStateFlow()
    
    init {
        _state.update { it.copy(isWeatherLoading = true) }
        viewModelScope.launch {
            getLocalWeatherUseCase().collect { weatherInfo ->
                _state.update { it.copy(weatherInfo = weatherInfo, isWeatherLoading = false) }
            }
        }
        viewModelScope.launch {
            getSavedLocationsUseCase().collect { savedLocations ->
                _state.update{ it.copy(savedLocations = savedLocations) }
            }
        }
        
        
    }
    
    fun onEvent(event: OverviewEvent) {
        when (event) {
            
            is OverviewEvent.OnSearchBarClick -> _state.update { it.copy(isSearchBarActive = true) }
            
            is OverviewEvent.OnQueryChange -> _state.update { it.copy(query = event.query) }
            
            is OverviewEvent.OnAddToSaved ->
                viewModelScope.launch { addSavedLocationUseCase(event.searchResult) }
            
            is OverviewEvent.OnDeleteFromSaved ->
                viewModelScope.launch { deleteSavedLocationUseCase(event.savedLocation) }
            
            is OverviewEvent.OnSearch -> {
                _state.update { it.copy(isSearchResultsLoading = true) }
                viewModelScope.launch {
                    val results = getSearchResultsUseCase(event.query)
                    _state.update {
                        it.copy(
                            searchResults = results.data ?: emptyList(),
                            isSearchResultsLoading = false
                        )
                    }
                }
            }
            
            is OverviewEvent.OnLocationButtonClick -> {
                _state.update {
                    it.copy(
                        isSearchBarActive = false,
                        isWeatherLoading = true,
                        query = ""
                    )
                }
                viewModelScope.launch {
                    getWeatherFromLocationUseCase()
                }
            }
            
            is OverviewEvent.OnBannerCollapse -> {
                _state.update {
                    it.copy(
                        isSearchBarActive = false,
                        searchResults = emptyList(),
                        query = ""
                    )
                }
            }
            
            is OverviewEvent.OnSearchResultClick -> {
                _state.update { it.copy(isWeatherLoading = true, isSearchBarActive = false) }
                viewModelScope.launch {
                    fetchAndStoreWeatherInfoUseCase(event.searchResult.coordinates)
                }
            }
            
            is OverviewEvent.OnPullRefresh -> {
                _state.update { it.copy(isWeatherLoading = true) }
                viewModelScope.launch {
                    _state.value.weatherInfo?.place?.coordinates?.let { fetchAndStoreWeatherInfoUseCase(it) }
                }
            }
        }
    }
}
