package com.enjot.materialweather.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enjot.materialweather.core.presentation.ui.components.ScreenHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .systemBarsPadding()
    ) {

        ScreenHeader(stringResource(R.string.settings), onNavigateBack)

        Spacer(modifier = Modifier.height(16.dp))

        BackgroundWorkSection(
            areBackgroundUpdatesEnabled = state.backgroundUpdatesEnabled,
            repeatInterval = state.backgroundUpdatesRepeatInterval,
            isLocationBased = state.areLocationBasedBackgroundUpdatesEnabled,
            toggleBackgroundUpdates = viewModel::toggleBackgroundUpdatesEnabled,
            onLocationBasedUpdatesClick = { TODO() },
            onSetInterval = viewModel::setBackgroundUpdatesInterval
        )

    }
}