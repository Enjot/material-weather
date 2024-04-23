package com.enjot.materialweather.ui.screen.overview.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.mapper.toSearchResult
import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.ui.core.location.CurrentLocationButton
import com.enjot.materialweather.ui.screen.overview.SearchState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableSearchBanner(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    selectedCity: String,
    searchState: SearchState,
    savedLocations: List<SavedLocation>,
    isActive: Boolean,
    onLocationButtonClick: () -> Unit,
    onArrowBackClick: () -> Unit,
    onExpand: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onSearchResultClick: (SearchResult) -> Unit,
    onAddToSaved: (SearchResult) -> Unit,
    onRemoveFromSaved: (SavedLocation) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    
    val padding by animateIntAsState(
        targetValue = if (isActive) 0 else 16,
        label = ""
    )
    
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            focusManager.clearFocus()
            onSearch()
        },
        active = isActive,
        onActiveChange = { onExpand() },
        placeholder = {
            Text(
                text = if (isActive) stringResource(R.string.search) else selectedCity,
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
            AnimatedContent(
                targetState = isActive, label = "",
                transitionSpec = {
                    (fadeIn(animationSpec = tween(220, delayMillis = 90))
                        .togetherWith(fadeOut(animationSpec = tween(90))))
                }
            ) { isActive ->
                if (isActive) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        if (searchState is SearchState.Loading) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(24.dp)
                                    .align(Alignment.CenterVertically),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
                        if (query != "") {
                            IconButton(onClick = {
                                keyboardController?.show()
                                focusRequester.requestFocus()
                                onQueryChange("")
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                } else {
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
            }
        },
        modifier = modifier
            .padding(
                start = padding.dp,
                end = padding.dp,
                top = padding.dp
            )
            .fillMaxWidth()
            .focusable()
            .focusRequester(focusRequester)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                
                item {
                    
                    if (searchState is SearchState.Error) {
                        Text(
                            text = searchState.message.asString(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .height(32.dp)
                                .padding(8.dp)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
                
                if (searchState is SearchState.Idle) {
                    items(searchState.results) { searchResult ->
                        SearchResultItem(
                            searchResult = searchResult,
                            onAddToSaved = {
                                focusManager.clearFocus()
                                onAddToSaved(searchResult)
                            },
                            onClick = { onSearchResultClick(searchResult) }
                        )
                    }
                }
                
                item {
                    Spacer(Modifier.height(12.dp))
                    CurrentLocationButton(
                        onClick = onLocationButtonClick
                    )
                }
                item {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = if (savedLocations.isNotEmpty()) stringResource(R.string.saved_places)
                        else stringResource(R.string.no_saved_places),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(12.dp))
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
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

@Preview
@Composable
fun ExpandableSearchBannerPreview() {
    
    ExpandableSearchBanner(
        query = "Sieniawa",
        onQueryChange = {},
        onSearch = {},
        selectedCity = "",
        searchState = SearchState.Idle(),
        savedLocations = emptyList(),
        isActive = true,
        onLocationButtonClick = { /*TODO*/ },
        onArrowBackClick = { /*TODO*/ },
        onExpand = { /*TODO*/ },
        onNavigateToSettings = { /*TODO*/ },
        onSearchResultClick = {},
        onAddToSaved = {},
        onRemoveFromSaved = {}
    )
}