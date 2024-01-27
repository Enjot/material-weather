package com.enjot.materialweather.ui.overviewscreen.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.model.AirPollution
import com.enjot.materialweather.domain.model.AirSingleParameter

@Composable
fun AirPollutionBanner(
    airPollution: AirPollution,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText(text = "Air Quality")
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    AirItem(item = airPollution.list[0])
                    AirItem(item = airPollution.list[1])
                    AirItem(item = airPollution.list[2])
                }
                
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    AirItem(item = airPollution.list[3])
                    AirItem(item = airPollution.list[4])
                    AirItem(item = airPollution.list[5])
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    AirItem(item = airPollution.list[6])
                    AirItem(item = airPollution.list[7])
                    AirItem(item = airPollution.list[8])
                }
            }
            Text(
                text = "μg/m³",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 8.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .width(100.dp)
                    .background(
                        MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.2f
                        )
                    )
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun AirItem(
    item: AirSingleParameter,
    modifier: Modifier = Modifier,
    width: Dp = 96.dp,
) {
    
    val textMeasurer = rememberTextMeasurer()
    
    val percentOfValue = (100f * item.value / item.range) / 100f
    val progress = if (percentOfValue < 1f) percentOfValue else 1f
    
    val color = MaterialTheme.colorScheme.primary.copy(
        alpha = 0.5f
    )
    val trackColor = MaterialTheme.colorScheme.background
    
    val textStyle = MaterialTheme.typography.titleMedium.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    val backgroundCircleColor = MaterialTheme.colorScheme.secondary
    
    val backgroundPathString =
        stringResource(id = R.string.zigzag_circle_path)
    val zigzagCirclePath = remember {
        PathParser().parsePathString(backgroundPathString)
            .toPath()
    }
    
    val infiniteTransition = rememberInfiniteTransition(label = "")
    
    val rotationDegree by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 10000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )
    
    Canvas(
        modifier = modifier
            .width(width)
            .height(width * 1.1f)
            .clickable { }
    ) {
        
        val arcWidthAndHeight = this.size.width * 0.8f
        
        val canvasTextStyle = textStyle.copy(
            fontSize = (arcWidthAndHeight / 4f).toSp()
        )
        
        val valueTextMeasure = textMeasurer.measure(
            text = item.value.toString(),
            style = canvasTextStyle
        )
        val nameTextMeasure = textMeasurer.measure(
            text = item.name,
            style = canvasTextStyle
        )
        val strokeWidth = arcWidthAndHeight * 0.15f
        
        val circleBounds = zigzagCirclePath.getBounds()
        val circleCenter = Offset(
            x = circleBounds.left + circleBounds.width / 2,
            y = circleBounds.top + circleBounds.height / 2
        )
        
        if (item.name == "AQI") {
            
            scale((this.size.height / 150f) * 1.3f) {
                translate(
                    left = (this.size.width - 150f) / 2,
                    top = (arcWidthAndHeight * 1.05f - circleBounds.height + nameTextMeasure.size.height) / 2
                ) {
                    rotate(
                        degrees = rotationDegree,
                        pivot = circleCenter
                    ) {
                        drawPath(
                            path = zigzagCirclePath,
                            color = backgroundCircleColor,
                            alpha = 0.2f
                        )
                    }
                }
            }
        }
        drawArc(
            color = trackColor,
            topLeft = Offset(
                x = this.center.x - arcWidthAndHeight / 2,
                y = strokeWidth / 2
            ),
            startAngle = -220f,
            sweepAngle = 260f,
            size = Size(arcWidthAndHeight, arcWidthAndHeight),
            useCenter = false,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            ),
            alpha = 0.3f
        )
        drawArc(
            color = color,
            topLeft = Offset(
                x = this.center.x - arcWidthAndHeight / 2,
                y = strokeWidth / 2
            ),
            startAngle = -220f,
            sweepAngle = 260f * progress,
            size = Size(arcWidthAndHeight, arcWidthAndHeight),
            useCenter = false,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )
        drawText(
            textMeasurer = textMeasurer,
            text = item.value.toString(),
            topLeft = Offset(
                x = this.center.x - valueTextMeasure.size.width / 2,
                y = (arcWidthAndHeight - valueTextMeasure.size.height + strokeWidth) / 2
            ),
            style = canvasTextStyle
        )
        drawText(
            textMeasurer = textMeasurer,
            text = item.name,
            topLeft = Offset(
                x = this.center.x - nameTextMeasure.size.width / 2,
                y = this.size.height - nameTextMeasure.size.height
            ),
            style = canvasTextStyle
        )
    }
}


@Preview(
    apiLevel = 33, showBackground = true
)
@Composable
fun AirItemPreview() {
    AirItem(
        item = AirSingleParameter(
            name = "AQI",
            value = 100,
            range = 200
        ),
        width = 100.dp
    )
}

@Preview(
    apiLevel = 33,
    showBackground = false,
    device = "spec:width=1080px,height=2340px,dpi=440,isRound=true",
    backgroundColor = 0xFF000000
)
@Composable
private fun AirPollutionCardPreview() {
    AirPollutionBanner(
        airPollution = AirPollution(
            2,
            80,
            20,
            5550,
            70,
            120,
            200,
            40,
            80,
        ),
        modifier = Modifier.fillMaxWidth()
    )
}