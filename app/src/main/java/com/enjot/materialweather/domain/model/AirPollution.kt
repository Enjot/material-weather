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
    
    val coIndex = when (co) {
        in 0..<4400 -> 1
        in 4400..<9400 -> 2
        in 9400..<12400 -> 3
        in 12400..<15400 -> 4
        else -> 5
    }
    
    val no2Index = when (no2) {
        in 0..<40 -> 1
        in 40..<70 -> 2
        in 70..<150 -> 3
        in 150..<200 -> 4
        else -> 5
    }
    
    val o3Index = when (o3) {
        in 0..<60 -> 1
        in 60..<100 -> 2
        in 100..<140 -> 3
        in 140..<180 -> 4
        else -> 5
    }
    
    val pm10Index = when (pm10) {
        in 0..<20 -> 1
        in 20..<50 -> 2
        in 50..<100 -> 3
        in 100..<200 -> 4
        else -> 5
    }
    
    val pm25Index = when (pm25) {
        in 0..<10 -> 1
        in 10..<25 -> 2
        in 25..<50 -> 3
        in 50..<75 -> 4
        else -> 5
    }
    
    val so2Index = when (so2) {
        in 0..<20 -> 1
        in 20..<80 -> 2
        in 80..<250 -> 3
        in 250..<350 -> 4
        else -> 5
    }
    
    val list = listOf(
        AirSingleParameter("PM2.5", pm25, pm25Range),
        AirSingleParameter("PM10", pm10, pm10Range),
        AirSingleParameter("CO", co, coRange),
        AirSingleParameter("O3", o3, o3Range),
        AirSingleParameter("NO", no, noRange),
        AirSingleParameter("NO2", no2, no2Range),
        AirSingleParameter("SO2", so2, so2Range),
        AirSingleParameter("NH3", nh3, nh3Range),
    )
}

data class AirSingleParameter(
    val name: String,
    val value: Int,
    val range: Int,
    val progression: Float = (value * 100f / range) / 100f
)