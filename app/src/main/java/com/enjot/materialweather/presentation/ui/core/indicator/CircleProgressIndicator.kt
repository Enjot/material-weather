package com.enjot.materialweather.presentation.ui.core.indicator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.presentation.ui.theme.onProgressContainer
import com.enjot.materialweather.presentation.ui.theme.progressContainer

@Composable
fun CircleProgressIndicator(
    progress: Int,
    range: Int,
    modifier: Modifier = Modifier,
    minText: String = "",
    rangeText: String = "",
) {
    val circlePathString =
        stringResource(id = R.string.zigzag_circle_path)
    val circlePath = remember {
        PathParser().parsePathString(circlePathString)
            .toPath()
    }
    val textMeasurer = rememberTextMeasurer()
    
    val color = MaterialTheme.colorScheme.onProgressContainer
    val trackColor = MaterialTheme.colorScheme.progressContainer
    val textStyle = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    val percent: Float = when {
        progress >= range -> 1f
        progress != 0 && range != 0 -> {
            progress.toFloat() * 100f / range.toFloat() / 100f
        }
        else -> 0f
    }
    Canvas(
        modifier = modifier
            .height(100.dp)
            .width(80.dp)
    ) {
        val size = this.size
        val bounds = circlePath.getBounds()
        val currentSize = size
        val currentOffset = Offset(
            x = 0f,
            y = bounds.height - (bounds.height * percent)
        )
        
        val current = Path().apply {
            addRect(Rect(offset = currentOffset, size = currentSize))
        }
        
        translate(
            left = this.center.x - bounds.width / 2,
            top = this.center.y - bounds.height / 2,
        ) {
                drawPath(
                    path = circlePath,
                    color = trackColor
                )
                clipPath(
                    path = circlePath
                ) {
                    drawPath(
                        path = current,
                        color = color
                    )
            }

        }
        val rangeTextBounds = textMeasurer.measure(rangeText, textStyle)
        val rangeTextTopLeft = Offset(
            x = (this.size.width - rangeTextBounds.size.width) / 2,
            y = 0f
        )
        drawText(
            textMeasurer = textMeasurer,
            text = rangeText,
            style = textStyle,
            topLeft = rangeTextTopLeft
        )
        val minTextBounds = textMeasurer.measure(minText, textStyle)
        val minTextTopLeft = Offset(
            x = (this.size.width - minTextBounds.size.width) / 2,
            y = (this.size.height - minTextBounds.size.height)
        )
        drawText(
            textMeasurer = textMeasurer,
            text = minText,
            style = textStyle,
            topLeft = minTextTopLeft
        )
    }
}

@Preview
@Composable
fun CircleProgressIndicatorPreveiew() {
    CircleProgressIndicator(progress = 0, range = 11)
}