package com.enjot.materialweather.weather.data.database

import com.enjot.materialweather.weather.data.mapper.toSavedLocation
import com.enjot.materialweather.weather.data.mapper.toSavedLocationEntity
import com.enjot.materialweather.weather.data.mapper.toWeatherEntity
import com.enjot.materialweather.weather.data.mapper.toWeatherInfo
import com.enjot.materialweather.weather.domain.model.SavedLocation
import com.enjot.materialweather.weather.domain.model.WeatherInfo
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalRepositoryImpl(
    private val savedLocationsDao: SavedLocationDao,
    private val weatherDao: WeatherDao
): LocalRepository {
    override fun getLocalWeather(): Flow<WeatherInfo> =
        weatherDao.getWeather().map { it?.toWeatherInfo() ?: WeatherInfo() }

    override suspend fun saveLocalWeather(weatherInfo: WeatherInfo) {
        val weatherEntity = weatherInfo.toWeatherEntity()
        weatherDao.insertWeather(weatherEntity)
    }

    override fun getSavedLocations(): Flow<List<SavedLocation>> {
        return savedLocationsDao.getSavedLocations().map { list ->
            list.map { entity ->
                entity.toSavedLocation()
            }
        }
    }

    override suspend fun addSavedLocation(savedLocation: SavedLocation) {
        savedLocationsDao.insertSavedLocation(savedLocation.toSavedLocationEntity())
    }

    override suspend fun deleteSavedLocation(savedLocation: SavedLocation) {
        savedLocationsDao.deleteSavedLocation(savedLocation.toSavedLocationEntity())
    }
}