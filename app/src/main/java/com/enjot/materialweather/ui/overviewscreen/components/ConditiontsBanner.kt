package com.enjot.materialweather.ui.overviewscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.domain.model.WeatherConditions

@Composable
fun ConditionsBanner(
    conditions: WeatherConditions
) {
    Column(Modifier) {
        TitleText(text = "Conditions")
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text("Sunrise ${conditions.sunrise}")
                Text("Sunset ${conditions.sunset}")
                Text("Wind speed ${conditions.windSpeed} km/h")
                Text("Wind deg ${conditions.windDeg}°")
                Text("Humidity ${conditions.humidity}%")
                Text("Dew point ${conditions.dewPoint}°")
                Text("UV Index ${conditions.uvi}")
                Text("Pressure ${conditions.pressure} hPa")
            }
        }
    }

}