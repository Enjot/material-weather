package com.enjot.materialweather.weather.presentation.ui.core.indicator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.weather.presentation.ui.theme.onProgressContainer
import com.enjot.materialweather.weather.presentation.ui.theme.progressContainer

@Composable
fun MoonPhaseIndicator(
    phase: Float,
    modifier: Modifier = Modifier
) {
    
    val moonColor = MaterialTheme.colorScheme.onProgressContainer
    val backGroundColor = MaterialTheme.colorScheme.progressContainer
    
    Canvas(modifier = modifier.size(100.dp)) {
        
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = size.minDimension / 2
        val center = Offset(x = canvasWidth / 2, y = canvasHeight / 2)
        
        val backgroundPath = Path().apply {
            addOval(
                Rect(
                    center.x - radius,
                    center.y - radius,
                    center.x + radius,
                    center.y + radius
                )
            )
        }
        
        clipPath(backgroundPath) {
            // Background circle (full moon)
            drawCircle(
                color = moonColor,
                center = center,
                radius = radius
            )
            
            // Foreground circle to create the moon phase effect
            if (phase in 0.0..1.0) {
                
                val scale = 1f
                
                val phaseShift = if (phase <= 0.5) radius * scale * 2 * (phase * 2)
                else -(radius * scale * 2) * (1f - phase) * 2f
                
                drawCircle(
                    color = backGroundColor,
                    center = Offset(x = center.x - phaseShift, y = center.y),
                    radius = radius * scale
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFE0E1EB)
@Composable
fun PreviewMoonPhase() {
    
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            MoonPhaseIndicator(phase = 0.0f)
            MoonPhaseIndicator(phase = 0.1f)
            MoonPhaseIndicator(phase = 0.2f)
            MoonPhaseIndicator(phase = 0.3f)
            MoonPhaseIndicator(phase = 0.4f)
            MoonPhaseIndicator(phase = 0.4f)
            MoonPhaseIndicator(phase = 0.45f)
            MoonPhaseIndicator(phase = 0.5f)
        }
        Column {
            MoonPhaseIndicator(phase = 0.6f)
            MoonPhaseIndicator(phase = 0.7f)
            MoonPhaseIndicator(phase = 0.8f)
            MoonPhaseIndicator(phase = 0.9f)
            MoonPhaseIndicator(phase = 1.0f)
        }
    }
}