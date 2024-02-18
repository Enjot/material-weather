package com.enjot.materialweather.ui.daily

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DailyScreen(
    id: Int?
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "$id",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}