package com.enjot.materialweather.ui.features.conditions

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.ui.core.indicator.CapsuleProgressIndicator

@Composable
fun HumidityCard(
    humidity: Int,
    dewPoint: Int,
    modifier: Modifier = Modifier
) {
    ConditionCard(
        title = stringResource(R.string.humidity),
        headline = humidity.toString(),
        headlineExtra = "%",
        description = stringResource(R.string.dew_point, dewPoint),
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