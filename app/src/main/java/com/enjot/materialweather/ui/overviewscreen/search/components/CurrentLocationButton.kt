package com.enjot.materialweather.ui.overviewscreen.search.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CurrentLocationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    
    val style = MaterialTheme.typography.titleMedium
    val iconScaleFactor = 1.3f
    
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = "Use current location",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(style.fontSize.value.dp * iconScaleFactor)
        )
    }
}

@Preview
@Composable
fun UseCurrentLocationButtonPreview() {
    
    CurrentLocationButton(
        onClick = { }
    )
}