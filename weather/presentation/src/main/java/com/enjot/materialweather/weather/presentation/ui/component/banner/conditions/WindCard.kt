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
import com.enjot.materialweather.weather.presentation.ui.core.indicator.ArrowDegreeIndicator

@Composable
fun WindCard(
    speed: Int,
    degree: Int,
    modifier: Modifier = Modifier
) {
    
    val speedState by animateIntAsState(targetValue = speed, label = "")
    val degreeState by animateIntAsState(targetValue = degree, label = "")
    
    ConditionCard(
        title = stringResource(R.string.wind),
        headline = speedState.toString(),
        description = stringResource(R.string.km_h),
        headlineExtra = "",
        modifier = modifier
    ) {
        ArrowDegreeIndicator(
            degree = degreeState,
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
