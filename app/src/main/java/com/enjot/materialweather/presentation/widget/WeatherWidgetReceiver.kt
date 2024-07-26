package com.enjot.materialweather.presentation.widget

import androidx.glance.appwidget.GlanceAppWidgetReceiver

class WeatherWidgetReceiver : GlanceAppWidgetReceiver() {
    
    override val glanceAppWidget = WeatherWidget()
}