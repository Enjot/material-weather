package com.enjot.materialweather.data.repository

import com.enjot.materialweather.data.database.SavedLocationDao
import com.enjot.materialweather.data.database.WeatherDao
import com.enjot.materialweather.data.mapper.toSavedLocation
import com.enjot.materialweather.data.mapper.toSavedLocationEntity
import com.enjot.materialweather.data.mapper.toWeatherEntity
import com.enjot.materialweather.data.mapper.toWeatherInfo
import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
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