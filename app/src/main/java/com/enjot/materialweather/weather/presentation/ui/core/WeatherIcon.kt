package com.enjot.materialweather.weather.presentation.ui.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import com.enjot.materialweather.R

@Composable
fun WeatherIcon(
    iconCode: String,
    modifier: Modifier = Modifier
) {
    
    val density = LocalDensity.current
    val sizeInDp = with(density) { 100.toDp() }
    
    val drawableId = when (iconCode) {
        "01d" -> R.drawable._01d
        "01n" -> R.drawable._01n
        "02d" -> R.drawable._02d
        "02n" -> R.drawable._02n
        "03d" -> R.drawable._03d
        "03n" -> R.drawable._03n
        "04d" -> R.drawable._04d
        "04n" -> R.drawable._04n
        "09d" -> R.drawable._09d
        "09n" -> R.drawable._09n
        "10d" -> R.drawable._10d
        "10n" -> R.drawable._10n
        "11d" -> R.drawable._11d
        "11n" -> R.drawable._11n
        "13d" -> R.drawable._13d
        "13n" -> R.drawable._13n
        "50d" -> R.drawable._50d
        "50n" -> R.drawable._50n
        else -> 0
    }
    
    if (drawableId != 0) {
        Image(
            painter = painterResource(drawableId),
            contentDescription = null,
            modifier = modifier
                .size(sizeInDp)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.5f
                    )
                )
        )
    } else {
        Icon(
            imageVector = Icons.Filled.Block,
            contentDescription = null,
            modifier = modifier
                .size(sizeInDp)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.5f
                    )
                )
        )
    }
}