package com.enjot.materialweather.presentation.overviewscreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.domain.model.CurrentWeather

@Composable
fun SummaryBanner(
    current: CurrentWeather
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${current.temp}°",
            style = MaterialTheme.typography.displayLarge,
        )
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = current.description,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "feels like ${current.feelsLike}°",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}
