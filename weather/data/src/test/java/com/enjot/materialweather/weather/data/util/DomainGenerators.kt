package com.enjot.materialweather.weather.data.util

import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.weather.domain.model.AirPollution
import com.enjot.materialweather.weather.domain.model.CurrentWeather
import com.enjot.materialweather.weather.domain.model.DailyWeather
import com.enjot.materialweather.weather.domain.model.HourlyWeather
import com.enjot.materialweather.weather.domain.model.SavedLocation
import com.enjot.materialweather.weather.domain.model.SearchResult
import com.enjot.materialweather.weather.domain.model.WeatherInfo


fun savedLocation() = SavedLocation(
    id = 1,
    name = "test name",
    postCode = "test post code",
    countryCode = "test country code",
    coordinates = coordinates()
)

fun weatherInfo() = WeatherInfo(
    place = place(),
    current = current(),
    hourly = hourlyList(),
    daily = dailyList(),
    airPollution = airPollution()
)

fun airPollution() = AirPollution(
    aqi = 2,
    nh3 = 50,
    no = 50,
    co = 50,
    no2 = 50,
    o3 = 50,
    pm10 = 50,
    pm25 = 50,
    so2 = 50
)

fun dailyList() = listOf(daily(), daily(), daily())

fun daily() = DailyWeather(
    dayOfWeek = "test dayofweek",
    sunrise = "0:00",
    sunset = "0:00",
    moonrise = "0:00",
    moonset = "0:00",
    moonPhase = 0.25,
    summary = "test summary",
    tempDay = 20,
    tempNight = 10,
    pressure = 1000,
    humidity = 50,
    dewPoint = 10,
    uvi = 2,
    clouds = 10,
    windSpeed = 10,
    windGust = 10.0,
    windDeg = 10,
    pop = 1.0,
    rainPrecipitation = 10.0,
    snowPrecipitation = 10.0,
    weather = "test weather",
    description = "test description",
    icon = "test icon"

)

fun hourlyList() = listOf(
    hourlyWeather(), hourlyWeather(), hourlyWeather()
)

fun hourlyWeather() = HourlyWeather(
    localFormattedTime = "0:00",
    temp = 20,
    humidity = 50,
    windSpeed = 10,
    windDeg = 180,
    pop = 3.0,
    rainPrecipitation = null,
    snowPrecipitation = null,
    icon = "test icon"
)

fun current() = CurrentWeather(
    localFormattedTime = "0:00",
    temp = 20,
    minTemp = 15,
    maxTemp = 25,
    feelsLike = 22,
    clouds = 10,
    windGust = null,
    rainPrecipitation = null,
    snowPrecipitation = null,
    weather = "test weather",
    description = "test description",
    icon = "icon test",
    conditions = conditions()
)

fun conditions() = CurrentWeather.WeatherConditions(
    sunrise = "0:00",
    sunset = "9:00",
    windSpeed = 10,
    windDeg = 180,
    humidity = 50,
    dewPoint = 10,
    pressure = 1000,
    uvi = 2
)


fun place() = SearchResult(
    city = "test city",
    postCode = "test postcode",
    countryCode = "test country code",
    coordinates = coordinates()
)

fun coordinates() = Coordinates(50.0, 50.0)

fun searchResult() = SearchResult(
    city = "Lorem",
    postCode = "12345",
    countryCode = "ABC",
    coordinates = coordinates()
)