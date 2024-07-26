package com.enjot.materialweather.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentWidth
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.enjot.materialweather.di.WidgetEntryPoint
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.usecase.weather.LocalWeatherFlow
import dagger.hilt.android.EntryPointAccessors

class WeatherWidget : GlanceAppWidget() {
    
    @SuppressLint("ResourceType", "SuspiciousIndentation")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        
        val appContext = context.applicationContext
        
        val getLocalWeatherUseCase = getLocalWeatherUseCase(appContext).invoke()
        
        provideContent {
            
            val state = getLocalWeatherUseCase.collectAsState(initial = WeatherInfo())
            
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = GlanceModifier.fillMaxSize().background(GlanceTheme.colors.secondaryContainer)
                ) {
                    
                    state.value.place?.city?.let { Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = GlanceTheme.colors.onSecondaryContainer
                        ),
                        modifier = GlanceModifier.padding(2.dp)
                    ) }
                    
                    state.value.current?.temp?.let {
                        Box(
                            modifier = GlanceModifier
                                .padding(2.dp)
                                .background(GlanceTheme.colors.primary)
                                .wrapContentWidth()
                                .cornerRadius(360)
                        ) {
                            Text(
                                text = "$it°",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onPrimary
                                ),
                                modifier = GlanceModifier.padding(2.dp)
                            )
                        }
                        
                    }
                    
                    state.value.current?.description?.let { Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = GlanceTheme.colors.onSecondaryContainer
                        ),
                        modifier = GlanceModifier.padding(2.dp)
                    ) }
                }
            }
        }
}

private fun getLocalWeatherUseCase(context: Context): LocalWeatherFlow {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(
        context, WidgetEntryPoint::class.java
    )
    return hiltEntryPoint.getLocalWeatherUseCase()
}