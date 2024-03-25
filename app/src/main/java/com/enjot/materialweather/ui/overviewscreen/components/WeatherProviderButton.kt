package com.enjot.materialweather.ui.overviewscreen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WeatherProviderButton(modifier: Modifier = Modifier) {
    
    val uriHandler = LocalUriHandler.current
    
    TextButton(
        onClick = { uriHandler.openUri("https://openweathermap.org/") },
        modifier = modifier
    ) {
        Text(
            text = "openweathermap.org",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }
}