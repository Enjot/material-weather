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
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Update weather in the background",
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
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true }
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Period between for background updates",
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
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
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
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Normal
                    )
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
    var interval by remember { mutableLongStateOf(repeatInterval) }
    
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
                    imageVector = Icons.Filled.Timer,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
                
                Text(
                    text = "Set interval period",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )

                SingleIntervalRadioButton(text = "30 minutes", selected = interval == 30L, onClick = { interval = 30L })
                SingleIntervalRadioButton(text = "60 minutes", selected = interval == 60L, onClick = { interval = 60L })
                SingleIntervalRadioButton(text = "2 hours", selected = interval == 120L, onClick = { interval = 120L })
                SingleIntervalRadioButton(text = "3 hours", selected = interval == 180L, onClick = { interval = 180L })
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = {
                        onSetInterval(interval)
                        onDismissRequest()
                    }
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}

@Composable
fun SingleIntervalRadioButton(text: String, selected:Boolean, onClick: () -> Unit) {
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
    IntervalDialog(repeatInterval = 15, onSetInterval = {}) {}
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BackgroundWorkSectionPreview() {
    BackgroundWorkSection(
        isWorkScheduled = true,
        repeatInterval = 15,
        isLocationBased = false,
        onBackgroundWeatherUpdatesClick = { /*TODO*/ },
        onLocationBasedUpdatesClick = { /*TODO*/ },
        onSetInterval = { /*TODO*/ }
    )
}