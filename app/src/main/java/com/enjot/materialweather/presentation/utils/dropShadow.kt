package com.enjot.materialweather.presentation.utils

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.apply

/**
 * Adds a drop shadow effect to the composable.
 *
 * This modifier allows you to draw a shadow behind the composable with various customization options.
 *
 * @param shape The shape of the shadow.
 * @param color The color of the shadow.
 * @param blur The blur radius of the shadow
 * @param offsetY The shadow offset along the Y-axis.
 * @param offsetX The shadow offset along the X-axis.
 * @param spread The amount to increase the size of the shadow.
 *
 * @return A new `Modifier` with the drop shadow effect applied.
 */
fun Modifier.dropShadow(
    shape: Shape = RectangleShape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {

    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint()
    paint.color = color

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}