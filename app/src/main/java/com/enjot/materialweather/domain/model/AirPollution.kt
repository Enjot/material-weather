package com.enjot.materialweather.domain.model

class AirPollution(
    val aqi: Int,
    val nh3: Double,
    val no: Double,
    val co: Double,
    val no2: Double,
    val o3: Double,
    val pm10: Double,
    val pm25: Double,
    val so2: Double
) {
    val aqiRange = 1..5
    val nh3Range = 0.1..200.0
    val noRange = 0.1..100.0
    
    val coIndex = when {
        co in 0.0 ..< 4400.0 -> 1
        co in 4400.0 ..< 9400.0 -> 2
        co in 9400.0 ..< 12400.0 -> 3
        co in 12400.0 ..< 15400.0 -> 4
        else -> 5
    }
    
    val no2Index = when {
        no2 in 0.0 ..< 40.0 -> 1
        no2 in 40.0 ..< 70.0 -> 2
        no2 in 70.0 ..< 150.0 -> 3
        no2 in 150.0 ..< 200.0 -> 4
        else -> 5
    }
    
    val o3Index = when {
        o3 in 0.0 ..< 60.0 -> 1
        o3 in 60.0 ..< 100.0 -> 2
        o3 in 100.0 ..< 140.0 -> 3
        o3 in 140.0 ..< 180.0 -> 4
        else -> 5
    }
    
    val pm10Index = when {
        pm10 in 0.0 ..< 20.0 -> 1
        pm10 in 20.0 ..< 50.0 -> 2
        pm10 in 50.0 ..< 100.0 -> 3
        pm10 in 100.0 ..< 200.0 -> 4
        else -> 5
    }
    
    val pm25Index = when {
        pm25 in 0.0 ..< 10.0 -> 1
        pm25 in 10.0 ..< 25.0 -> 2
        pm25 in 25.0 ..< 50.0 -> 3
        pm25 in 50.0 ..< 75.0 -> 4
        else -> 5
    }
    
    val so2Index = when (so2) {
        in 0.0 ..< 20.0 -> 1
        in 20.0 ..< 80.0 -> 2
        in 80.0  ..< 250.0 -> 3
        in 250.0 ..< 350.0 -> 4
        else -> 5
    }
    
}