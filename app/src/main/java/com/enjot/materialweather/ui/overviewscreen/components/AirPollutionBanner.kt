package com.enjot.materialweather.ui.overviewscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.domain.model.AirPollution
import com.enjot.materialweather.domain.model.AirSingleParameter

@Composable
fun AirPollutionBanner(
    airPollution: AirPollution,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        TitleText(text = "Air Quality Index: ${airPollution.aqi}/5")
        Card {
            Spacer(modifier = Modifier.height(16.dp))
            airPollution.list.forEach {
                AirItem(it)
            }
            Text(
                text = "μg/m³",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 8.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun AirItem(
    item: AirSingleParameter,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = item.name,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.weight(1f)
        )
        LinearProgressIndicator(
            color = MaterialTheme.colorScheme.tertiary,
            trackColor = MaterialTheme.colorScheme.tertiaryContainer,
            progress = item.progression,
            modifier = Modifier
                .weight(5f)
                .padding(horizontal = 16.dp)
                .clip(CircleShape)
        )
        Text(
            text = "${item.value}",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
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
        ),
        modifier = Modifier.fillMaxWidth()
    )
}