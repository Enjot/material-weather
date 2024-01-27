package com.enjot.materialweather.ui.overviewscreen.components.conditions

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PressureCard(
    pressure: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Pressure",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = pressure.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "mbar",
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Canvas(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomEnd)
            ) {
            
            }
        }
    }
}

@Preview
@Composable
fun PressureCardPreview() {
    PressureCard(pressure = 1000)
}