package com.enjot.materialweather.weather.presentation.ui.component.banner.conditions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.weather.presentation.ui.core.indicator.MoonPhaseIndicator
import com.enjot.materialweather.weather.presentation.utils.moonPhaseText

@Composable
fun MoonPhaseCard(
    phase: Float,
    modifier: Modifier = Modifier
) {
    
    val phaseState by animateFloatAsState(targetValue = phase, label = "")
    
    ConditionCard(
        title = stringResource(R.string.moon_phase),
        modifier = modifier
    ) {
        Text(
            text = stringResource(moonPhaseText(phaseState.toDouble())),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        )
        MoonPhaseIndicator(
            phase = phaseState,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(80.dp)
                .padding(12.dp)
        )
        
    }
}

@Composable
@Preview
fun MoonPhaseCardPreview() {
    
    MoonPhaseCard(phase = 0.2f, modifier = Modifier.width(250.dp))
}