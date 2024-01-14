package com.enjot.materialweather.data.mapper

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

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

fun Int.toLocalTime(): LocalTime {
    val timestampInMillis = this * 1000L
    
    return Instant.ofEpochMilli(timestampInMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
}