package com.enjot.materialweather.presentation.overviewscreen.searchbanner

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
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
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.presentation.overviewscreen.searchbanner.components.SearchResultItem
import com.enjot.materialweather.presentation.overviewscreen.searchbanner.components.UseCurrentLocationButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableSearchBanner(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    selectedCity: String,
    searchResults: List<SearchResult>,
    isActive: Boolean,
    onUseCurrentLocationClick: () -> Unit,
    onArrowBackClick: () -> Unit,
    onSearchBarClick: () -> Unit,
    onSearchResultClick: (SearchResult) -> Unit,
    onAddToFavorites: (SearchResult) -> Unit,
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
                        imageVector = Icons.Default.ArrowBack,
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
        trailingIcon = {},
        modifier = modifier
            .padding(
                start = padding.dp,
                end = padding.dp,
                top = padding.dp,
            )
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            
            searchResults.forEach { result ->
                SearchResultItem(
                    searchResult = result,
                    onAddToFavorites = { onAddToFavorites(result) },
                    onClick = {
                        onSearchResultClick(result)
                    }
                )
            }
            
            UseCurrentLocationButton(
                onClick = onUseCurrentLocationClick,
                modifier = Modifier.padding(16.dp)
            )
            
            Text(text = "Saved locations", modifier = Modifier.padding(16.dp))
        }
        
        /*
        It has to be in this ColumnScope, otherwise it override default back
        action in overview screen
         */
        
        BackHandler {
            onArrowBackClick()
        }
    }
}