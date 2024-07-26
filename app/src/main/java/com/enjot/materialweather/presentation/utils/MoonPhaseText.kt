package com.enjot.materialweather.presentation.utils

import com.enjot.materialweather.R

fun moonPhaseText(phase: Double): Int {
    
    return when (phase) {
        0.0, 1.0 -> R.string.new_moon
        in 0.0..<0.25 -> R.string.waxing_crescent
        0.25 -> R.string.first_quarter_moon
        in 0.25..<0.5 -> R.string.waxing_gibbous
        0.5 -> R.string.full_moon
        in 0.5..<0.75 -> R.string.waning_gibbous
        0.75 -> R.string.last_quarter_moon
        in 0.75..1.0 -> R.string.waning_crescent
        else -> R.string.unknown_phase
    }
}