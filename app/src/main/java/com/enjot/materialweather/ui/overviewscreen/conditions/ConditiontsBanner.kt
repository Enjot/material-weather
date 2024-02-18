package com.enjot.materialweather.ui.overviewscreen.conditions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.domain.model.CurrentWeather
import com.enjot.materialweather.ui.overviewscreen.components.Banner

@Composable
fun ConditionsBanner(
    conditions: CurrentWeather.WeatherConditions
) {
   val innerPadding = 8.dp
    
    Banner(
        title = "Conditions"
    ) {
        Row {
            WindCard(
                speed = conditions.windSpeed,
                degree = conditions.windDeg,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(innerPadding))
            HumidityCard(
                humidity = conditions.humidity,
                dewPoint = conditions.dewPoint,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(innerPadding))
        Row {
            UviCard(
                uvi = conditions.uvi,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(innerPadding))
            PressureCard(
                pressure = conditions.pressure,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
