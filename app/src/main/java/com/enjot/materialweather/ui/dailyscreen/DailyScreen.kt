package com.enjot.materialweather.ui.dailyscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.enjot.materialweather.ui.overviewscreen.conditions.ConditionsBanner

@Composable
fun DailyScreen(
    id: Int?,
    viewModel: DailyViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        
        state.current?.conditions?.let { ConditionsBanner(it) }
    }
    
}