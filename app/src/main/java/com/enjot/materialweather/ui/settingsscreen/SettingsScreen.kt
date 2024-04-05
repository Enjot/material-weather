package com.enjot.materialweather.ui.settingsscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.enjot.materialweather.ui.settingsscreen.backgroundwork.BackgroundWorkSection

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        
        SettingsHeader(onNavigateBack)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        BackgroundWorkSection(
            isWorkScheduled = state.isWorkScheduled,
            repeatInterval = state.repeatInterval,
            isLocationBased = state.isLocationBased,
            onBackgroundWeatherUpdatesClick = { onEvent(SettingsEvent.OnBackgroundWeatherUpdatesClick) },
            onLocationBasedUpdatesClick = { onEvent(SettingsEvent.OnLocationBasedClick) },
            onSetInterval = { minutes -> onEvent(SettingsEvent.OnSetIntervals(minutes)) },
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        
    }
}

@Composable
private fun SettingsHeader(
    onArrowBackButtonClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onArrowBackButtonClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back"
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge
        )
    }
}