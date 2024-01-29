package com.enjot.materialweather.data.mapper

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Int.toLocalDateTime(): LocalDateTime {
    val timestampInMillis = this * 1000L
    
    return Instant.ofEpochMilli(timestampInMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

fun Int.toLocalDate(): LocalDate {
    val timestampInMillis = this * 1000L
    
    return Instant.ofEpochMilli(timestampInMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun Int.toFormattedLocalTime(): String {
    val timestampInMillis = this * 1000L
    val localTime = Instant.ofEpochMilli(timestampInMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
    val pattern = "H:mm"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return localTime.format(formatter)
}

fun Int.toDayOfWeek(): String {
    val timestampInMillis = this * 1000L
    val day = Instant.ofEpochMilli(timestampInMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate().dayOfWeek.toString()
    
    return day.first().uppercase() + day.substring(1).lowercase()
}