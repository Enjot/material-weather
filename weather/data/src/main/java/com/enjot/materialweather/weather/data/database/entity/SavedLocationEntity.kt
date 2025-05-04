package com.enjot.materialweather.weather.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_location")
data class SavedLocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val postCode: String? = null,
    val countryCode: String,
    val lat: Double,
    val lon: Double
)
