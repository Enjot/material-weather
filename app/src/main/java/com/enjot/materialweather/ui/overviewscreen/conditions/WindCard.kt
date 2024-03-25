package com.enjot.materialweather.ui.overviewscreen.conditions

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.ui.reusable.ArrowDegreeIndicator

@Composable
fun WindCard(
    speed: Int,
    degree: Int,
    modifier: Modifier = Modifier
) {
    ConditionCard(
        title = "Wind",
        headline = speed.toString(),
        description = "km/h",
        headlineExtra = "",
        modifier = modifier
    ) {
        ArrowDegreeIndicator(
            degree = degree,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        )
    }
}

@Preview
@Composable
fun WindCardPreview() {
    WindCard(speed = 13, degree = 230)
}
