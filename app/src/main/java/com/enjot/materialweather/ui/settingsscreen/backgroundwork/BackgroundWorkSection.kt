package com.enjot.materialweather.ui.settingsscreen.backgroundwork

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun BackgroundWorkSection(
    isWorkScheduled: Boolean,
    repeatInterval: Long,
    isLocationBased: Boolean,
    onBackgroundWeatherUpdatesClick: () -> Unit,
    onLocationBasedUpdatesClick: () -> Unit,
    onSetInterval: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    
    var showDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier
    ) {
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onBackgroundWeatherUpdatesClick() }
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Background updates",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Switch(
                checked = isWorkScheduled,
                onCheckedChange = { onBackgroundWeatherUpdatesClick() },
                thumbContent = if (isWorkScheduled) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    null
                }
            )
        }
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true }
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Background updates interval",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (repeatInterval) {
                        in 0..60 -> "$repeatInterval minutes"
                        else -> "${repeatInterval / 60L} hours"
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Location based background updates",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Currently not available",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Switch(
                enabled = false,
                checked = isLocationBased,
                onCheckedChange = { onLocationBasedUpdatesClick() },
                thumbContent = if (isLocationBased) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    null
                }
            )
        }
        
    }
    if (showDialog) {
        IntervalDialog(
            repeatInterval = repeatInterval,
            onSetInterval = onSetInterval,
            onDismissRequest = { showDialog = false }
        )
    }
    
}

@Composable
private fun IntervalDialog(
    repeatInterval: Long,
    onSetInterval: (Long) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                
                Icon(
                    imageVector = Icons.Filled.Update,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
                
                Text(
                    text = "Background updates interval",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                
                SingleIntervalRadioButton(
                    text = "30 minutes",
                    selected = repeatInterval == 30L,
                    onClick = {
                        onSetInterval(30L)
                        onDismissRequest()
                    }
                )
                SingleIntervalRadioButton(
                    text = "60 minutes",
                    selected = repeatInterval == 60L,
                    onClick = {
                        onSetInterval(60L)
                        onDismissRequest()
                    }
                )
                SingleIntervalRadioButton(
                    text = "2 hours",
                    selected = repeatInterval == 120L,
                    onClick = {
                        onSetInterval(120L)
                        onDismissRequest()
                    }
                )
                SingleIntervalRadioButton(
                    text = "3 hours",
                    selected = repeatInterval == 180L,
                    onClick = {
                        onSetInterval(180L)
                        onDismissRequest()
                    }
                )
                
                SingleIntervalRadioButton(
                    text = "4 hours",
                    selected = repeatInterval == 240L,
                    onClick = {
                        onSetInterval(240L)
                        onDismissRequest()
                    }
                )
                
                TextButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
fun SingleIntervalRadioButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text)
    }
}

@Preview
@Composable
fun IntervalDialogPreview() {
    IntervalDialog(repeatInterval = 120L, onSetInterval = {}) {}
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BackgroundWorkSectionPreview() {
    BackgroundWorkSection(
        isWorkScheduled = true,
        repeatInterval = 120L,
        isLocationBased = false,
        onBackgroundWeatherUpdatesClick = { /*TODO*/ },
        onLocationBasedUpdatesClick = { /*TODO*/ },
        onSetInterval = { /*TODO*/ }
    )
}