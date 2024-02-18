package com.enjot.materialweather.ui.overviewscreen.conditions

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.ui.reusable.CapsuleProgressIndicator

@Composable
fun HumidityCard(
    humidity: Int,
    dewPoint: Int,
    modifier: Modifier = Modifier
) {
    ConditionCard(
        title = "Humidity",
        headline = humidity.toString(),
        headlineExtra = "%",
        description = "dew point $dewPoint°",
        modifier = modifier
    ) {
        
        CapsuleProgressIndicator(
            value = humidity,
            range = 100,
            valueText = "0",
            rangeText = "100",
            unit = "",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        )
    }
}


@Preview
@Composable
fun HumidityCardPreview() {
    HumidityCard(humidity = 100, dewPoint = 1)
}