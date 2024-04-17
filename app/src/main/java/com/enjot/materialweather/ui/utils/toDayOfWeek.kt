package com.enjot.materialweather.ui.utils

import com.enjot.materialweather.R

fun String.toDayOfWeekId(): Int = when (this) {
    "MONDAY" -> R.string.monday
    "TUESDAY" -> R.string.tuesday
    "WEDNESDAY" -> R.string.wednesday
    "THURSDAY" -> R.string.thursday
    "FRIDAY" -> R.string.friday
    "SATURDAY" -> R.string.saturday
    "SUNDAY" -> R.string.sunday
    else -> R.string.unknown
}
