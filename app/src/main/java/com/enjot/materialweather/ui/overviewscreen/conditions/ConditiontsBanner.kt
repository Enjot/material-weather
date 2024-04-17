package com.enjot.materialweather.ui.overviewscreen.conditions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.model.DailyWeather
import com.enjot.materialweather.ui.overviewscreen.components.Banner

@Composable
fun ConditionsBanner(
    daily: DailyWeather
) {
   val innerPadding = 8.dp
    
    Banner(
        title = stringResource(R.string.current_conditions)
    ) {
        Row {
            WindCard(
                speed = daily.windSpeed,
                degree = daily.windDeg,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(innerPadding))
            HumidityCard(
                humidity = daily.humidity,
                dewPoint = daily.dewPoint,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(innerPadding))
        Row {
            UviCard(
                uvi = daily.uvi,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(innerPadding))
            PressureCard(
                pressure = daily.pressure,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(innerPadding))
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
}
