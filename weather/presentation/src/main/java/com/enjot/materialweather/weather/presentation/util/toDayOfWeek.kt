package com.enjot.materialweather.weather.presentation.util

import com.enjot.materialweather.weather.presentation.R

fun toDayOfWeekId(day: String): Int = when (day) {
    "MONDAY" -> R.string.monday
    "TUESDAY" -> R.string.tuesday
    "WEDNESDAY" -> R.string.wednesday
    "THURSDAY" -> R.string.thursday
    "FRIDAY" -> R.string.friday
    "SATURDAY" -> R.string.saturday
    "SUNDAY" -> R.string.sunday
    else -> R.string.unknown
}
