package com.enjot.materialweather.weather.presentation.ui.screen.overview.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.weather.domain.model.SearchResult

@Composable
fun SearchResultItem(
    searchResult: SearchResult,
    onClick: (SearchResult) -> Unit,
    onAddToSaved: (SearchResult) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(searchResult)
            }
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 4.dp)
    ) {
        Text(
            text = searchResult.city,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        if (searchResult.postCode != null) {
            Text(
                text = searchResult.postCode!!,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = searchResult.countryCode,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = { onAddToSaved(searchResult) }) {
            Icon(imageVector = Icons.Outlined.AddCircleOutline, contentDescription = "add to saved")
        }
    }
}

@Preview
@Composable
fun SearchResultItemPreview() {
    
    val searchResult = SearchResult(
        city = "London",
        postCode = "WC2N 5DX",
        countryCode = "GB",
        coordinates = Coordinates(0.0, 0.0)
    )
    
    SearchResultItem(
        searchResult = searchResult,
        onAddToSaved = { },
        onClick = {  }
    )
}