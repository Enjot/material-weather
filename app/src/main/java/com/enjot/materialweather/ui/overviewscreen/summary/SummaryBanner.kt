package com.enjot.materialweather.ui.overviewscreen.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.domain.model.CurrentWeather
import com.enjot.materialweather.ui.overviewscreen.components.Banner

@Composable
fun SummaryBanner(
    current: CurrentWeather
) {
    Banner {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text(
                text = "${current.temp}°",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = current.description,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text(
                text = "feels like ${current.feelsLike}°",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.alignBy(LastBaseline)
            )
            Text(
                text = "last update ${current.localFormattedTime}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.alignBy(LastBaseline)
            )
        }
    }
}