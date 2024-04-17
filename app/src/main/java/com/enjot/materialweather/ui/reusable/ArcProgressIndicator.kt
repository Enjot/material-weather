package com.enjot.materialweather.ui.reusable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.ui.theme.onProgressContainer
import com.enjot.materialweather.ui.theme.progressContainer

@Composable
fun ArcProgressIndicator(
    value: Int,
    range: Int,
    valueText: String,
    rangeText: String,
    unit: String,
    modifier: Modifier = Modifier,
    height: Dp = 80.dp
) {
    val textMeasurer = rememberTextMeasurer()
    
    val progress = getProgressValue(value, range)
    
    val color = MaterialTheme.colorScheme.onProgressContainer
    val trackColor = MaterialTheme.colorScheme.progressContainer
    val textStyle = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    
    Canvas(
        modifier = modifier
            .height(height)
            .width(height * 1.4f)
    ) {
        val startAngle = -220f
        val sweepAngle = 260f
        val arcDiameter = this.size.height * 0.7f
        val strokeWidth = arcDiameter * 0.25f
        val progressArcTopLeft = Offset(
            x = this.center.x - arcDiameter / 2,
            y = this.center.y - arcDiameter / 2
        )
        val progressArcSize = Size(
            width = arcDiameter,
            height = arcDiameter
        )
        drawArc(
            color = trackColor,
            topLeft = progressArcTopLeft,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            size = progressArcSize,
            useCenter = false,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )
        drawArc(
            color = color,
            topLeft = progressArcTopLeft,
            startAngle = startAngle,
            sweepAngle = sweepAngle * progress,
            size = progressArcSize,
            useCenter = false,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )
        
        val unitTextBounds = textMeasurer.measure(unit, textStyle)
        val unitTextTopLeft = Offset(
            x = this.center.x - unitTextBounds.size.width / 2,
            y = this.center.y - unitTextBounds.size.height / 2
        )
        drawText(
            textMeasurer = textMeasurer,
            text = unit,
            style = textStyle,
            topLeft = unitTextTopLeft
        )
        val valueTextBounds =
            textMeasurer.measure(value.toString(), textStyle)
        val valueTextTopLeft = Offset(
            x = this.size.width * 0.35f - valueTextBounds.size.width,
            y = arcDiameter + this.size.height * 0.1f
        )
        val rangeTextTopLeft = Offset(
            x = this.size.width - this.size.width * 0.35f,
            y = arcDiameter + this.size.height * 0.1f
        )
        drawText(
            textMeasurer = textMeasurer,
            text = valueText,
            style = textStyle,
            topLeft = valueTextTopLeft
        )
        drawText(
            textMeasurer = textMeasurer,
            text = rangeText,
            style = textStyle,
            topLeft = rangeTextTopLeft
        )
    }
}

private fun getProgressValue(value: Int, range: Int): Float {
    val percent = (100f * value / range) / 100f
    return if (percent < 1f) percent else 1f
}

@Preview(showBackground = true, backgroundColor = 0xFFDBE2E8)
@Composable
fun PressureArcProgressBarPreview() {
    ArcProgressIndicator(
        value = 1,
        range = 1000,
        valueText = "73",
        rangeText = "180",
        unit = ""
    )
}