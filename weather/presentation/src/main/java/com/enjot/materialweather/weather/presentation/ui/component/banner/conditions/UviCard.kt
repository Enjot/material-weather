package com.enjot.materialweather.weather.presentation.ui.component.banner.conditions

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.weather.presentation.R
import com.enjot.materialweather.weather.presentation.ui.core.indicator.CircleProgressIndicator

@Composable
fun UviCard(
    uvi: Int,
    modifier: Modifier = Modifier
) {
    // multiply by 10 because otherwise, there is no animation if value change by 1
    val uviState by animateIntAsState(targetValue = uvi * 10, label = "")
    val uviHighestValue = 11
    val level = when (uviState / 10) {
        in 0..2 -> stringResource(R.string.uvi_low)
        in 3..5 -> stringResource(R.string.uvi_moderate)
        in 6..7 -> stringResource(R.string.uvi_high)
        else -> stringResource(R.string.uvi_very_high)
    }
    
    ConditionCard(
        title = stringResource(R.string.uv_index),
        headline = (uviState / 10).toString(),
        description = level,
        modifier = modifier
    ) {
        CircleProgressIndicator(
            progress = uviState,
            range = uviHighestValue * 10,
            minText = "0",
            rangeText = "11+",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical = 2.dp)
        )
    }
}

@Preview
@Composable
fun UviCardPreview() {
    UviCard(uvi = 3, modifier = Modifier.width(180.dp))
}