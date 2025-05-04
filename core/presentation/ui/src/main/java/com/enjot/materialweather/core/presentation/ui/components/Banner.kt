package com.enjot.materialweather.core.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Banner(
    title: String? = null,
    content: @Composable ColumnScope. () -> Unit,
) {
    Column(
    modifier = Modifier
        .fillMaxWidth()
    ) {
        title?.let { TitleText(text = title) }
        content()
    }
}