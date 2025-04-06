package com.enjot.materialweather.presentation.ui.screen.overview.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveCircleOutline
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
import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.SavedLocation

@Composable
fun SavedLocationItem(
    savedLocation: SavedLocation,
    onClick: (SavedLocation) -> Unit,
    onRemove: (SavedLocation) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(savedLocation)
            }
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 4.dp)
    ) {
        Text(
            text = savedLocation.name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        if (savedLocation.postCode != null) {
            Text(
                text = savedLocation.postCode,
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
            text = savedLocation.countryCode,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = { onRemove(savedLocation) }) {
            Icon(
                imageVector = Icons.Outlined.RemoveCircleOutline,
                contentDescription = "remove from saved")
        }
    }
}

@Preview
@Composable
fun SavedLocationItemPreview() {
    
    val savedLocation = SavedLocation(
        name = "London",
        postCode = "WC2N 5DX",
        countryCode = "GB",
        coordinates = Coordinates(0.0, 0.0)
    )
    
    SavedLocationItem(
        savedLocation = savedLocation,
        onRemove = { },
        onClick = {  }
    )
}