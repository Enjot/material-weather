package com.enjot.materialweather.ui.features.conditions

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.ui.core.indicator.ArcProgressIndicator
import kotlin.math.roundToInt

@Composable
fun PressureCard(
    pressure: Int,
    modifier: Modifier = Modifier
) {
    ConditionCard(
        title = stringResource(R.string.pressure),
        headline = pressure.toString(),
        description = stringResource(R.string.mbar),
        modifier = modifier
    ) {
        
        val low = 900f
        val high = 1100f
        val percent =
            ((pressure.toFloat() - low) / (high - low) * 100f).roundToInt()
        
        ArcProgressIndicator(
            unit = "",
            value = percent,
            range = 100,
            valueText = stringResource(R.string.low),
            rangeText = stringResource(R.string.high),
            height = 80.dp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    vertical = 8.dp
                )
        )
    }
}


@Preview
@Composable
fun PressureCardPreview() {
    PressureCard(pressure = 1035)
}