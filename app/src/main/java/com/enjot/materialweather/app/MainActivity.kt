package com.enjot.materialweather.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.enjot.materialweather.core.presentation.designsystem.theme.MaterialWeatherTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.Forest.d("onCreate()")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialWeatherTheme {
                Surface(modifier = Modifier.Companion.fillMaxSize()) { AppNavigation() }
            }
        }
    }
}