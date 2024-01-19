package com.enjot.materialweather.presentation.overviewscreen.searchbanner.components

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UseCurrentLocationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = "Use current location",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
fun UseCurrentLocationButtonPreview() {
    
    UseCurrentLocationButton(
        onClick = { }
    )
}