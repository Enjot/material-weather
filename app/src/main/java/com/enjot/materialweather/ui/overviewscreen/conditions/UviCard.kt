package com.enjot.materialweather.ui.overviewscreen.conditions

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.ui.reusable.CircleProgressIndicator

@Composable
fun UviCard(
    uvi: Int,
    modifier: Modifier = Modifier
) {
    val level = when (uvi) {
        in 0..2 -> "low"
        in 3..5 -> "moderate"
        in 6..7 -> "high"
        else -> "very high"
    }
    
    ConditionCard(
        title = "UV Index",
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