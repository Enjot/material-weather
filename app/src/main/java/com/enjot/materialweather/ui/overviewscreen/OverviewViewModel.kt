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
    
    fun onIntent(event: OverviewIntent) {
        when (event) {
            
            is OverviewIntent.OnSearchBarClick -> _state.update { it.copy(isSearchBarActive = true) }
            
            is OverviewIntent.OnQueryChange -> _state.update { it.copy(query = event.query) }
            
            is OverviewIntent.OnAddToSaved ->
                viewModelScope.launch { addSavedLocationUseCase(event.searchResult) }
            
            is OverviewIntent.OnDeleteFromSaved ->
                viewModelScope.launch { deleteSavedLocationUseCase(event.savedLocation) }
            
            is OverviewIntent.OnSearch -> {
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
            
            is OverviewIntent.OnLocationButtonClick -> {
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
            
            is OverviewIntent.OnBannerCollapse -> {
                _state.update {
                    it.copy(
                        isSearchBarActive = false,
                        searchResults = emptyList(),
                        query = ""
                    )
                }
            }
            
            is OverviewIntent.OnSearchResultClick -> {
                _state.update { it.copy(isWeatherLoading = true, isSearchBarActive = false) }
                viewModelScope.launch {
                    fetchAndStoreWeatherInfoUseCase(event.searchResult.coordinates)
                }
            }
            
            is OverviewIntent.OnPullRefresh -> {
                _state.update { it.copy(isWeatherLoading = true) }
                viewModelScope.launch {
                    _state.value.weatherInfo?.place?.coordinates?.let { fetchAndStoreWeatherInfoUseCase(it) }
                }
            }
        }
    }
}
