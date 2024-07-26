package com.enjot.materialweather.presentation.ui.banner.airpollution

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.enjot.materialweather.presentation.ui.core.indicator.ArcProgressIndicator

@Composable
fun ArcProgressBar(
    unit: String,
    value: Int,
    range: Int,
    modifier: Modifier = Modifier,
    name: String? = null,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        name?.let {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        ArcProgressIndicator(
            value = value,
            range = range,
            unit = unit,
            valueText = value.toString(),
            rangeText = range.toString()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF4DEC5)
@Composable
fun ArcProgressBarPreview() {
    ArcProgressBar(unit = "mbar", value = 1020, range = 1800, name = "Pressure")
}