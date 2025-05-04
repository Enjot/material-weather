package com.enjot.materialweather.widget

import android.content.Context
import androidx.glance.appwidget.updateAll

internal class WidgetManagerImpl(
    private val context: Context
) : WidgetManager {

    override suspend fun updateWidgets() {
        WeatherWidget().updateAll(context)
    }
}