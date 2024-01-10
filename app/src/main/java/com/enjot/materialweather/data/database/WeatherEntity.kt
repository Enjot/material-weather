package com.enjot.materialweather.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey val id: Int? = null
)
