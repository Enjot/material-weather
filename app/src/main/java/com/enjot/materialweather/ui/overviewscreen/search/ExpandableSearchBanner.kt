package com.enjot.materialweather.ui.overviewscreen.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.domain.mapper.toSearchResult
import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.ui.overviewscreen.search.components.SavedLocationItem
import com.enjot.materialweather.ui.overviewscreen.search.components.SearchResultItem
import com.enjot.materialweather.ui.overviewscreen.search.locationbutton.CurrentLocationButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableSearchBanner(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    selectedCity: String,
    searchResults: List<SearchResult>,
    savedLocations: List<SavedLocation>,
    isActive: Boolean,
    onLocationButtonClick: () -> Unit,
    onArrowBackClick: () -> Unit,
    onSearchBarClick: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onSearchResultClick: (SearchResult) -> Unit,
    onAddToSaved: (SearchResult) -> Unit,
    onRemoveFromSaved: (SavedLocation) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val padding by animateIntAsState(
        targetValue = if (isActive) 0 else 16,
        label = ""
    )
    
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            focusManager.clearFocus()
            onSearch("")
        },
        active = isActive,
        onActiveChange = { onSearchBarClick() },
        placeholder = {
            Text(
                text = if (isActive) "Search" else selectedCity,
                style = MaterialTheme.typography.titleMedium
            )
        },
        leadingIcon = {
            if (isActive) {
                IconButton(onClick = onArrowBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            }
            
        },
        trailingIcon = {
            AnimatedVisibility(
                enter = fadeIn(),
                exit = fadeOut(),
                visible = isActive
            ) {
                IconButton(onClick = {
                    focusManager.clearFocus()
                    onNavigateToSettings()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
            .padding(
                start = padding.dp,
                end = padding.dp,
                top = padding.dp
            )
            .fillMaxWidth()
    ) {
        /*
        It has to be in this LazyColumnScope, otherwise it override default back
        action in overview screen
         */
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            items(searchResults) { searchResult ->
                SearchResultItem(
                    searchResult = searchResult,
                    onAddToSaved = {
                        focusManager.clearFocus()
                        onAddToSaved(searchResult)
                    },
                    onClick = { onSearchResultClick(searchResult) }
                )
            }
            item {
                CurrentLocationButton(
                    onClick = onLocationButtonClick,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                Text(
                    text = if (savedLocations.isNotEmpty()) "Saved locations" else "No saved locations",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(savedLocations) { savedLocation ->
                SavedLocationItem(
                    savedLocation = savedLocation,
                    onClick = { onSearchResultClick(savedLocation.toSearchResult()) },
                    onRemove = {
                        focusManager.clearFocus()
                        onRemoveFromSaved(savedLocation)
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}