package com.enjot.materialweather.ui.widget

import androidx.glance.appwidget.GlanceAppWidgetReceiver

class WeatherWidgetReceiver : GlanceAppWidgetReceiver() {
    
    override val glanceAppWidget = WeatherWidget()
}