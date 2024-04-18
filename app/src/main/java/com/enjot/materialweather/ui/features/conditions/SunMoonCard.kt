package com.enjot.materialweather.ui.features.conditions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.domain.model.DailyWeather

@Composable
fun SunMoonCard(
    daily: DailyWeather
) {
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sunrise: ${daily.sunrise}")
                Text("Sunset: ${daily.sunset}")
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Moonrise: ${daily.moonrise}")
                Text("Moonset: ${daily.moonset}")
            }
            Text(
                text = "Moon phase: ${daily.moonPhase}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        
    }
}