package com.enjot.materialweather.ui.features.conditions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.model.DailyWeather

@Composable
fun SunMoonCard(
    daily: DailyWeather,
    modifier: Modifier = Modifier
) {
    
    ConditionCard(
        title = stringResource(R.string.sun_and_moon),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            SingleRow(stringRes = R.string.sunrise, time = daily.sunrise)
            SingleRow(stringRes = R.string.sunset, time = daily.sunset)
            Spacer(modifier = Modifier.height(6.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(6.dp))
            SingleRow(stringRes = R.string.moonrise, time = daily.moonrise)
            SingleRow(stringRes = R.string.moonset, time = daily.moonset)
        }
    }
    
}

@Composable
private fun SingleRow(stringRes: Int, time: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(stringRes),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alignBy(FirstBaseline)
        )
        Text(
            text = time,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.alignBy(FirstBaseline)
        )
    }
}

@Composable
@Preview
fun SunMoonPreview() {
    
    SunMoonCard(
        daily = DailyWeather(
            dayOfWeek = "Wednesday",
            sunrise = "7:00",
            sunset = "17:00",
            moonrise = "19:30",
            moonset = "4:30",
            moonPhase = 4.5,
            summary = "inceptos",
            tempDay = 3094,
            tempNight = 2634,
            pressure = 3055,
            humidity = 8423,
            dewPoint = 3434,
            uvi = 5708,
            clouds = 5249,
            windSpeed = 2473,
            windGust = null,
            windDeg = 1498,
            pop = 6.7,
            rainPrecipitation = null,
            snowPrecipitation = null,
            weather = "sea",
            description = "signiferumque",
            icon = "amet"
        ),
        modifier = Modifier.width(220.dp)
    )
}