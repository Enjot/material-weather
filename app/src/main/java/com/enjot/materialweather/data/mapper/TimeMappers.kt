package com.enjot.materialweather.data.mapper

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Int.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochSecond(this.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

fun Int.toLocalDate(): LocalDate {
    return Instant.ofEpochSecond(this.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun Int.toFormattedLocalTime(): String {
    val localTime = Instant.ofEpochSecond(this.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
    return localTime.format(DateTimeFormatter.ofPattern("H:mm"))
}

fun Int.toDayOfWeek(): String {
    return Instant.ofEpochSecond(this.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalDate().dayOfWeek.toString()
}
