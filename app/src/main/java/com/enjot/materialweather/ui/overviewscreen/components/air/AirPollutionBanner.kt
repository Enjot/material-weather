package com.enjot.materialweather.ui.overviewscreen.components.air

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enjot.materialweather.R
import com.enjot.materialweather.domain.model.AirPollution
import com.enjot.materialweather.ui.overviewscreen.components.TitleText
import com.enjot.materialweather.ui.reusable.ArcProgressBar

@Composable
fun AirPollutionBanner(
    airPollution: AirPollution,
    modifier: Modifier = Modifier
) {
    
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
    val localDensity = LocalDensity.current
    
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText(text = "Air Quality")
        Card(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(360.dp)
                    .padding(8.dp)
            ) {
                ArcProgressBar(
                    name = airPollution.pm25Name,
                    unit = "μg/m³",
                    value = airPollution.pm25,
                    range = airPollution.pm25Range,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                ArcProgressBar(
                    name = airPollution.pm10Name,
                    unit = "μg/m³",
                    value = airPollution.pm10,
                    range = airPollution.pm10Range,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                ArcProgressBar(
                    name = airPollution.coName,
                    unit = "μg/m³",
                    value = airPollution.co,
                    range = airPollution.coRange,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
                ArcProgressBar(
                    name = airPollution.o3Name,
                    unit = "μg/m³",
                    value = airPollution.o3,
                    range = airPollution.o3Range,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                ArcProgressBar(
                    name = airPollution.aqiName,
                    unit = "index",
                    value = airPollution.aqi,
                    range = airPollution.aqiRange,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable { }
                        .drawBehind {
                            
                            val circleBounds = zigzagCirclePath.getBounds()
                            val circleCenter = Offset(
                                x = circleBounds.left + circleBounds.width / 2,
                                y = circleBounds.top + circleBounds.height / 2
                            )
                            
                            scale(
                                (this.size.width / 100f) / 1.3f
                            ) {
                                translate(
                                    left = (this.size.width - circleBounds.width) / 2,
                                    // TODO 1.85f fits better with my screen but I have to make it scalable with dp
                                    top = (this.size.height - circleBounds.height) / 1.85f
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
                )
                ArcProgressBar(
                    name = airPollution.noName,
                    unit = "μg/m³",
                    value = airPollution.no,
                    range = airPollution.noRange,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
                ArcProgressBar(
                    name = airPollution.no2Name,
                    unit = "μg/m³",
                    value = airPollution.no2,
                    range = airPollution.no2Range,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
                ArcProgressBar(
                    name = airPollution.so2Name,
                    unit = "μg/m³",
                    value = airPollution.so2,
                    range = airPollution.so2Range,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
                ArcProgressBar(
                    name = airPollution.nh3Name,
                    unit = "μg/m³",
                    value = airPollution.nh3,
                    range = airPollution.nh3Range,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
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