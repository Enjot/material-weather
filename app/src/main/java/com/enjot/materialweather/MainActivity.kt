package com.enjot.materialweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.enjot.materialweather.presentation.navigation.AppNavigation
import com.enjot.materialweather.presentation.ui.theme.MaterialWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialWeatherTheme {
                Surface(modifier = Modifier.fillMaxSize()) { AppNavigation() }
            }
        }
    }
}
