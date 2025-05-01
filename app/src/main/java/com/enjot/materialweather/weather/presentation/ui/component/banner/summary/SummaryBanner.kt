package com.enjot.materialweather.weather.presentation.ui.component.banner.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.weather.presentation.ui.core.Banner
import com.enjot.materialweather.weather.presentation.utils.conditionCodeToDescriptionStringRes

@Composable
fun SummaryBanner(
    temp: Int,
    description: String,
    feelsLike: Int? = null,
    localFormattedTime: String? = null
) {
    Banner {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "$temp°",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = stringResource(
                    conditionCodeToDescriptionStringRes(description)
                ),
                style = MaterialTheme.typography.titleLarge
            )
        }
        feelsLike?.let { feels ->
            localFormattedTime?.let { time ->
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.feels_like, feels),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.alignBy(LastBaseline)
                    )
                    Text(
                        text = stringResource(R.string.last_update, time),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.alignBy(LastBaseline)
                    )
                }
            }
        }
    }
}