package com.enjot.materialweather.presentation.ui.banner.conditions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.model.DailyWeather
import com.enjot.materialweather.presentation.ui.core.Banner

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
        Spacer(Modifier.height(innerPadding))
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
        Row {
            SunMoonCard(
                daily = daily,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(innerPadding))
            MoonPhaseCard(
                phase = daily.moonPhase.toFloat(),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
