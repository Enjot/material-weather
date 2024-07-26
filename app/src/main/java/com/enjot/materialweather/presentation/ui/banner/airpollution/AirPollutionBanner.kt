package com.enjot.materialweather.presentation.ui.banner.airpollution

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.model.AirPollution
import com.enjot.materialweather.presentation.ui.core.Banner

@Composable
fun AirPollutionBanner(
    airPollution: AirPollution
) {
    Banner(title = stringResource(R.string.air_quality)) {
        Card {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 400.dp)
                    .padding(12.dp)
            ) {
                ArcProgressBar(
                    name = airPollution.pm25Name,
                    unit = "μg/m³",
                    value = airPollution.pm25,
                    range = airPollution.pm25Range,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                ArcProgressBar(
                    name = airPollution.pm10Name,
                    unit = "μg/m³",
                    value = airPollution.pm10,
                    range = airPollution.pm10Range,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                ArcProgressBar(
                    name = airPollution.coName,
                    unit = "μg/m³",
                    value = airPollution.co,
                    range = airPollution.coRange,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
                ArcProgressBar(
                    name = airPollution.o3Name,
                    unit = "μg/m³",
                    value = airPollution.o3,
                    range = airPollution.o3Range,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                ArcProgressBar(
                    name = airPollution.aqiName,
                    unit = stringResource(R.string.index),
                    value = airPollution.aqi,
                    range = airPollution.aqiRange,
                    modifier = Modifier.align(Alignment.Center)
                )
                ArcProgressBar(
                    name = airPollution.noName,
                    unit = "μg/m³",
                    value = airPollution.no,
                    range = airPollution.noRange,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
                ArcProgressBar(
                    name = airPollution.no2Name,
                    unit = "μg/m³",
                    value = airPollution.no2,
                    range = airPollution.no2Range,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
                ArcProgressBar(
                    name = airPollution.so2Name,
                    unit = "μg/m³",
                    value = airPollution.so2,
                    range = airPollution.so2Range,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
                ArcProgressBar(
                    name = airPollution.nh3Name,
                    unit = "μg/m³",
                    value = airPollution.nh3,
                    range = airPollution.nh3Range,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}


@Preview(
    apiLevel = 33,
    device = "spec:width=1080px,height=2340px,dpi=440,isRound=true",
    backgroundColor = 0xFFFEFAFE, showBackground = true
)
@Composable
private fun AirPollutionCardPreview() {
    AirPollutionBanner(
        airPollution = AirPollution(
            2,
            80,
            20,
            5550,
            70,
            120,
            200,
            40,
            80,
        )
    )
}