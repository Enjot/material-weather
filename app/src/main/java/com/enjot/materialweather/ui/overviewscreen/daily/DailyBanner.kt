package com.enjot.materialweather.ui.overviewscreen.daily

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.domain.model.DailyWeather
import com.enjot.materialweather.ui.overviewscreen.components.AsyncIcon
import com.enjot.materialweather.ui.overviewscreen.components.Banner
import kotlin.math.roundToInt

@Composable
fun DailyBanner(
    daily: List<DailyWeather>,
    onClick: () -> Unit = {}
) {
    Banner(title = "Daily") {
        daily.forEachIndexed { index, item ->
            DailyItem(
                item = item,
                isFirst = index == 0,
                isLast = index == daily.size - 1
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DailyItem(
    item: DailyWeather,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { },
        shape = RoundedCornerShape(
            bottomStart = if (isLast) 16.dp else 4.dp,
            bottomEnd = if (isLast) 16.dp else 4.dp,
            topStart = if (isFirst) 16.dp else 4.dp,
            topEnd = if (isFirst) 16.dp else 4.dp
        ),
        modifier = modifier.padding(
            top = if (isFirst) 0.dp else 1.dp,
            bottom = if (isLast) 0.dp else 1.dp
        )
    ) {
        val color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
            alpha = 0.05f
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val bounds = this.size
                    val path = Path().apply {
                        moveTo(bounds.width / 2 - bounds.width * 0.2f, bounds.height)
                        lineTo(bounds.width / 2 - 12.dp.toPx(), 0f)
                        lineTo(bounds.width, 0f)
                        lineTo(bounds.width, bounds.height)
                        close()
                    }
                    drawPath(
                        path = path,
                        color = color
                    )
                }
                .padding(vertical = 8.dp, horizontal = 16.dp)
        
        ) {
            Text(
                text = if (isFirst) "Today" else item.dayOfWeek,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(3f)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(4f).fillMaxSize()
            ) {
                val popPercent = (item.pop * 100).roundToInt()
                Text(
                    text = if (popPercent > 0) "$popPercent%" else "",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.weight(2f).padding(end = 12.dp)
                )
                AsyncIcon(iconCode = item.icon, modifier = Modifier.weight(2f))
                Text(
                    text = "${item.tempDay}°/${item.tempNight}°",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(3f)
                )
            }
        }
    }
}