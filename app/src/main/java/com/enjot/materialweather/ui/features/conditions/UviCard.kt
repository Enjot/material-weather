package com.enjot.materialweather.ui.features.conditions

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.ui.core.indicator.CircleProgressIndicator

@Composable
fun UviCard(
    uvi: Int,
    modifier: Modifier = Modifier
) {
    val level = when (uvi) {
        in 0..2 -> stringResource(R.string.low)
        in 3..5 -> stringResource(R.string.moderate)
        in 6..7 -> stringResource(R.string.high)
        else -> stringResource(R.string.very_high)
    }
    
    ConditionCard(
        title = stringResource(R.string.uv_index),
        headline = uvi.toString(),
        description = level,
        modifier = modifier
    ) {
        CircleProgressIndicator(
            progress = uvi,
            range = 11,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        )
    }
}

@Preview
@Composable
fun UviCardPreview() {
    UviCard(uvi = 3)
}