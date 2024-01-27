package com.enjot.materialweather.ui.overviewscreen.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.domain.model.HourlyWeather
import kotlin.math.roundToInt

@Composable
fun HourlyBanner(
    hourly: List<HourlyWeather>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
    ) {
        TitleText(text = "Hourly")
        
        val color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
            alpha = 0.05f
        )
        
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
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (popPercent > 0) "$popPercent%" else "",
            style = MaterialTheme.typography.labelSmall
        )
        AsyncIcon(iconCode = item.icon)
        Text(
            text = if (isNow) "Now" else "${item.localTime}",
            style = MaterialTheme.typography.labelMedium
        )
    }
}