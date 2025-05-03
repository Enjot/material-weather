package com.enjot.materialweather.weather.presentation.ui.component.banner.conditions

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.weather.presentation.R
import com.enjot.materialweather.weather.presentation.ui.core.indicator.CapsuleProgressIndicator

@Composable
fun HumidityCard(
    humidity: Int,
    dewPoint: Int,
    modifier: Modifier = Modifier
) {
    val humidityState by animateIntAsState(targetValue = humidity, label = "")
    val dewPointState by animateIntAsState(targetValue = dewPoint, label = "")
    
    ConditionCard(
        title = stringResource(R.string.humidity),
        headline = humidityState.toString(),
        headlineExtra = "%",
        description = stringResource(R.string.dew_point, dewPointState),
        modifier = modifier
    ) {
        
        CapsuleProgressIndicator(
            value = humidityState,
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