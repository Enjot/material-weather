package com.enjot.materialweather.presentation.overviewscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage

@Composable
fun AsyncIcon(
    iconCode: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model =
        "https://openweathermap.org/img/wn/$iconCode@2x.png",
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.inversePrimary)
            
    )
}