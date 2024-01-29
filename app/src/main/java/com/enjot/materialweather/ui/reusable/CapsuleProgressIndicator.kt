package com.enjot.materialweather.ui.reusable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.ui.theme.onProgressContainer
import com.enjot.materialweather.ui.theme.progressContainer

@Composable
fun CapsuleProgressIndicator(
    value: Int,
    range: Int,
    valueText: String,
    rangeText: String,
    unit: String,
    modifier: Modifier = Modifier,
    height: Dp = 100.dp
) {
    val textMeasurer = rememberTextMeasurer()
    
    val progress = getProgressValue(value, range)
    
    val color = MaterialTheme.colorScheme.onProgressContainer
    val trackColor = MaterialTheme.colorScheme.progressContainer
    val textStyle = MaterialTheme.typography.labelSmall.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    
    
    val arrowPathString =
        stringResource(id = R.string.arrow_indicator_path)
    val arrowPath = remember {
        PathParser().parsePathString(arrowPathString)
            .toPath()
    }
    
    Canvas(
        modifier = modifier
            .height(height)
            .width(height * 0.4f)
    ) {
        val canvasTextStyle = textStyle.copy(
            fontSize = (this.size.height * 0.15f).toSp()
        )
        val rangeTextBounds =
            textMeasurer.measure(rangeText, canvasTextStyle)
        val rangeTextTopLeft = Offset(
            x = (this.size.width - rangeTextBounds.size.width) / 2,
            y = 0f
        )
        drawText(
            textMeasurer = textMeasurer,
            text = rangeText,
            style = canvasTextStyle,
            topLeft = rangeTextTopLeft
        )
        
        val valueTextBounds =
            textMeasurer.measure(valueText, canvasTextStyle)
        val valueTextTopLeft = Offset(
            x = (this.size.width - valueTextBounds.size.width) / 2,
            y = this.size.height - valueTextBounds.size.height
        )
        drawText(
            textMeasurer = textMeasurer,
            text = valueText,
            style = canvasTextStyle,
            topLeft = valueTextTopLeft
        )
        
        val width = this.size.width * 0.7f
        val centerX = this.center.x
        val startY = this.size.height - valueTextBounds.size.height * 1.7f
        val endY = valueTextBounds.size.height * 1.7f
        val capsuleHeight = this.size.height - valueTextBounds.size.height - rangeTextBounds.size.height // good
        val lineStart = Offset(centerX, startY)
        val lineEnd = Offset(centerX, endY)
        val lineCurrent = Offset(
            x = centerX,
            y = startY - (startY - endY) * progress
        )
        val arrowScale = 0.2f
        drawLine(
            color = trackColor,
            start = lineStart,
            end = lineEnd,
            strokeWidth = width,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = lineStart,
            end = lineCurrent,
            strokeWidth = width,
            cap = StrokeCap.Round
        )
        val arrowBounds = arrowPath.getBounds()
        
        translate(
            left = (this.size.width - width) / 2 - arrowBounds.width
        ) {
            scale(
                scale = arrowScale,
                pivot = Offset(arrowBounds.width, rangeTextBounds.size.height.toFloat())
            ) {
                val top = 0f
                val bottom = capsuleHeight / arrowScale
                val arrowPosition = (top + (bottom - top) / 100f) * (100f - progress * 100f)
                translate(
                    top = arrowPosition
                ) {
                    drawPath(
                        path = arrowPath,
                        color = color
                    )
                }
                
            }
            
        }
        
    }
}


private fun getProgressValue(value: Int, range: Int): Float {
    val percent = (100f * value / range) / 100f
    return if (percent < 1f) percent else 1f
}

@Preview(showBackground = true, backgroundColor = 0xFFDBE2E8)
@Composable
fun CapsuleProgressIndicatorPreview() {
    CapsuleProgressIndicator(
        value = 92,
        range = 100,
        valueText = "0",
        rangeText = "100",
        unit = ""
    )
}