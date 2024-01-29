package com.enjot.materialweather.ui.overviewscreen.conditions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.unit.dp

@Composable
fun ConditionCard(
    title: String,
    headline: String,
    description: String,
    modifier: Modifier = Modifier,
    headlineExtra: String = "",
    content: @Composable BoxScope. () -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(12.dp)
            ) {
                Row {
                    Text(
                        text = headline,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.alignBy(LastBaseline)
                    )
                    Text(
                        text = headlineExtra,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.alignBy(LastBaseline)
                    )
                }
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            content()
        }
    }
}