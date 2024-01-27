package com.enjot.materialweather.domain.model

class AirPollution(
    val aqi: Int,
    val nh3: Int,
    val no: Int,
    val co: Int,
    val no2: Int,
    val o3: Int,
    val pm10: Int,
    val pm25: Int,
    val so2: Int
) {
    
    // every range starts from 0
    val aqiRange = 5
    val nh3Range = 200
    val noRange = 100
    val coRange = 15400
    val no2Range = 200
    val o3Range = 180
    val pm10Range = 200
    val pm25Range = 75
    val so2Range = 350
    
    val aqiName = "AQI"
    val nh3Name = "NH3"
    val noName = "NO"
    val coName = "CO"
    val no2Name = "NO2"
    val o3Name = "O3"
    val pm10Name = "PM10"
    val pm25Name = "PM2.5"
    val so2Name = "SO2"
}