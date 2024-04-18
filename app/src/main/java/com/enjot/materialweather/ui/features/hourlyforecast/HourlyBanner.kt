package com.enjot.materialweather.ui.features.hourlyforecast

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.model.HourlyWeather
import com.enjot.materialweather.ui.core.Banner
import com.enjot.materialweather.ui.core.WeatherIcon
import kotlin.math.roundToInt

@Composable
fun HourlyBanner(
    hourly: List<HourlyWeather>
) {
    val scrollState = rememberScrollState()
    val color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
        alpha = 0.05f
    )
    Banner(title = stringResource(R.string.hourly_forecast)) {
        Card {
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .fillMaxWidth()
                    .drawBehind {
                        drawRect(
                            color = color,
                            topLeft = Offset(
                                x = 0f,
                                y = this.size.height - (this.size.height / 3)
                            ),
                            size = Size(
                                width = this.size.width,
                                height = this.size.height / 3
                            )
                        )
                    }
            ) {
                hourly.forEachIndexed { index, item ->
                    HourlyItem(item, index == 0)
                }
            }
        }
    }
}

@Composable
private fun HourlyItem(
    item: HourlyWeather,
    isNow: Boolean,
    modifier: Modifier = Modifier
) {
    val popPercent = (item.pop * 100).roundToInt()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 4.dp)
            .padding(8.dp)
    ) {
        Text(
            text = "${item.temp}°",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (popPercent > 0) "$popPercent%" else "",
            style = MaterialTheme.typography.labelSmall
        )
        WeatherIcon(iconCode = item.icon)
        Text(
            text = if (isNow) stringResource(R.string.now) else item.localFormattedTime,
            style = MaterialTheme.typography.bodySmall
        )
    }
}