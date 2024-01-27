package com.enjot.materialweather.ui.overviewscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import coil.compose.AsyncImage

@Composable
fun AsyncIcon(
    iconCode: String,
    modifier: Modifier = Modifier
) {
    
    /*
    I do this, because all images I get from openweathermap are 100x100px .pngs
    and I want them to be sharp on every screen and to occupy space even
    before they are loaded or if they are not loaded at all
    the solution is for now
     */
    
    val density = LocalDensity.current
    val sizeInDp = with(density) { 100.toDp() }
    
    AsyncImage(
        model =
        "https://openweathermap.org/img/wn/$iconCode@2x.png",
        contentDescription = null,
        modifier = modifier
            .size(sizeInDp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(
                alpha = 0.5f
            ))
    )
}