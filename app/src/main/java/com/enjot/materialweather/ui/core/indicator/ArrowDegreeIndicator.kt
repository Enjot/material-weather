package com.enjot.materialweather.ui.core.indicator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.ui.theme.onProgressContainer
import com.enjot.materialweather.ui.theme.progressContainer
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ArrowDegreeIndicator(
    degree: Int,
    modifier: Modifier = Modifier
) {
    val arrowColor = MaterialTheme.colorScheme.onProgressContainer
    val arcColor = MaterialTheme.colorScheme.progressContainer
    
    val arrowPathString =
        stringResource(id = R.string.arrow_indicator_path)
    val arrowPath = remember {
        PathParser().parsePathString(arrowPathString)
            .toPath()
    }
    val degrees = degree.toFloat() + 90f
    Canvas(
        modifier = modifier
            .size(90.dp)
    ) {
        
        val size = this.size
        
        drawArc(
            color = arcColor,
            startAngle = degrees - 45f,
            sweepAngle = 90f,
            topLeft = Offset.Zero,
            size = size,
            useCenter = false,
            style = Stroke(
                width = 10f,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(30f, 20f)
                )
            )
        )
        val angleInRad = ((degrees * PI) / 180).toFloat()
        val radius = this.center.x
        drawCircle(
            color = arrowColor,
            radius = 15f,
            center = Offset(
                x = this.center.x + radius * cos(angleInRad),
                y = this.center.y + radius * sin(angleInRad)
            )
        )
        val arrowBounds = arrowPath.getBounds()
        
        translate(
            left = this.center.x - arrowBounds.center.x,
            top = this.center.y - arrowBounds.center.y
        ) {
            rotate(
                degrees = degrees,
                pivot = arrowBounds.center
            ) {
                scale(
                    scale = 1.2f,
                    pivot = arrowBounds.center
                ) {
                    drawPath(
                        path = arrowPath,
                        color = arrowColor
                    )
                }
            }
        }
        
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF4DEC5)
@Composable
fun ArrowDegreeIndicatorPreview() {
    ArrowDegreeIndicator(degree = 90)
}