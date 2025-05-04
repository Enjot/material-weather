package com.enjot.materialweather.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.Action
import androidx.glance.action.action
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.weather.domain.model.CurrentWeather
import com.enjot.materialweather.weather.domain.model.SearchResult
import com.enjot.materialweather.weather.domain.model.WeatherInfo
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import com.enjot.materialweather.weather.presentation.util.conditionCodeToDescriptionStringRes
import com.enjot.materialweather.widget.WeatherWidget.Companion.RECTANGLE
import com.enjot.materialweather.widget.WeatherWidget.Companion.WIDE_RECTANGLE
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class WeatherWidget : GlanceAppWidget(), KoinComponent {

    companion object {
        internal val SQUARE = DpSize(100.dp, 100.dp)
        internal val RECTANGLE = DpSize(150.dp, 100.dp)
        internal val WIDE_RECTANGLE = DpSize(200.dp, 100.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(
            SQUARE,
            RECTANGLE,
            WIDE_RECTANGLE
        )
    )

    private val localRepository: LocalRepository by inject()

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val launchIntent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)!!
            .apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP }

        provideContent {
            val state by localRepository.getLocalWeather().collectAsState(initial = WeatherInfo())
            Content(
                state = state,
                onClick = actionStartActivity(launchIntent)
            )
        }
    }
}

@Composable
private fun Content(
    state: WeatherInfo,
    onClick: Action
) {
    val size = LocalSize.current

    Column(
        horizontalAlignment = Alignment.Start,
        verticalAlignment = Alignment.Top,
        modifier = GlanceModifier
            .fillMaxSize()
            .background(GlanceTheme.colors.primaryContainer)
            .clickable(onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier.fillMaxWidth()
        ) {
            state.current?.temp?.let {
                Text(
                    text = "$it°",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = GlanceTheme.colors.onPrimaryContainer

                    )
                )
            }

            if (size.width >= WIDE_RECTANGLE.width) {
                state.current?.icon?.let {
                    Spacer(GlanceModifier.width(8.dp))
                    WeatherIcon(iconCode = it)
                }
            }

            if (size.width >= RECTANGLE.width) {
                Spacer(GlanceModifier.defaultWeight())

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        state.place?.city?.let {
                            Image(
                                provider = ImageProvider(R.drawable.ic_distance),
                                contentDescription = it,
                                colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimaryContainer),
                                modifier = GlanceModifier.size(16.dp)
                            )
                            Spacer(GlanceModifier.width(4.dp))
                            Text(
                                text = it,
                                style = TextStyle(
                                    color = GlanceTheme.colors.onPrimaryContainer
                                )
                            )
                        }
                    }
                    Spacer(GlanceModifier.width(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        state.current?.localFormattedTime?.let {
                            Image(
                                provider = ImageProvider(R.drawable.ic_update),
                                contentDescription = it,
                                colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimaryContainer),
                                modifier = GlanceModifier.size(16.dp)
                            )
                            Spacer(GlanceModifier.width(4.dp))
                            Text(
                                text = it,
                                style = TextStyle(
                                    color = GlanceTheme.colors.onPrimaryContainer
                                )
                            )
                        }
                    }
                }
            }
        }

        if (size.width >= RECTANGLE.width) {
            Spacer(GlanceModifier.defaultWeight())
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
            ) {
                Spacer(GlanceModifier.defaultWeight())
                state.current?.description?.let {
                    Text(
                        text = LocalContext.current.getString(conditionCodeToDescriptionStringRes(it)),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = GlanceTheme.colors.onPrimaryContainer
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherIcon(
    iconCode: String,
    modifier: GlanceModifier = GlanceModifier
) {
    val drawableId = when (iconCode) {
        "01d" -> com.enjot.materialweather.weather.presentation.R.drawable._01d
        "01n" -> com.enjot.materialweather.weather.presentation.R.drawable._01n
        "02d" -> com.enjot.materialweather.weather.presentation.R.drawable._02d
        "02n" -> com.enjot.materialweather.weather.presentation.R.drawable._02n
        "03d" -> com.enjot.materialweather.weather.presentation.R.drawable._03d
        "03n" -> com.enjot.materialweather.weather.presentation.R.drawable._03n
        "04d" -> com.enjot.materialweather.weather.presentation.R.drawable._04d
        "04n" -> com.enjot.materialweather.weather.presentation.R.drawable._04n
        "09d" -> com.enjot.materialweather.weather.presentation.R.drawable._09d
        "09n" -> com.enjot.materialweather.weather.presentation.R.drawable._09n
        "10d" -> com.enjot.materialweather.weather.presentation.R.drawable._10d
        "10n" -> com.enjot.materialweather.weather.presentation.R.drawable._10n
        "11d" -> com.enjot.materialweather.weather.presentation.R.drawable._11d
        "11n" -> com.enjot.materialweather.weather.presentation.R.drawable._11n
        "13d" -> com.enjot.materialweather.weather.presentation.R.drawable._13d
        "13n" -> com.enjot.materialweather.weather.presentation.R.drawable._13n
        "50d" -> com.enjot.materialweather.weather.presentation.R.drawable._50d
        "50n" -> com.enjot.materialweather.weather.presentation.R.drawable._50n
        else -> 0
    }

    if (drawableId != 0) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = GlanceModifier
                .size(40.dp)
                .cornerRadius(1000.dp)
                .background(GlanceTheme.colors.primary.getColor(LocalContext.current).copy(0.5f))
        ) {
            Image(
                provider = ImageProvider(drawableId),
                contentDescription = null,
                modifier = modifier.size(36.dp)
            )
        }
    }
}

private val weatherInfo = WeatherInfo(
    current = CurrentWeather(
        temp = 15,
        icon = "01d",
        description = "300",
        localFormattedTime = "12:00",
        minTemp = 4174,
        maxTemp = 3220,
        feelsLike = 4612,
        clouds = 7929,
        windGust = 0.1,
        rainPrecipitation = 2.3,
        snowPrecipitation = 4.5,
        weather = "clear sky",
        conditions = CurrentWeather.WeatherConditions(
            sunrise = "6:00",
            sunset = "20:00",
            windSpeed = 5985,
            windDeg = 4401,
            humidity = 9310,
            dewPoint = 1974,
            pressure = 7941,
            uvi = 5637

        ),
    ),
    place = SearchResult(
        city = "London", postCode = "discere", countryCode = "Peru", coordinates = Coordinates(0.0, 0.0)
    )
)

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 90, heightDp = 90)
@Composable
fun WeatherWidgetSquarePreview() {
    GlanceTheme {
        Content(
            state = weatherInfo,
            onClick = action {}
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 190, heightDp = 100)
@Composable
fun WeatherWidgetRectanglePreview() {
    GlanceTheme {
        Content(
            state = weatherInfo,
            onClick = action {}
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 250, heightDp = 100)
@Composable
fun WeatherWidgetWideRectanglePreview() {
    GlanceTheme {
        Content(
            state = weatherInfo,
            onClick = action {}
        )
    }
}