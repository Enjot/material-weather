package com.enjot.materialweather.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.enjot.materialweather.domain.util.place
import com.enjot.materialweather.domain.util.savedLocation
import com.enjot.materialweather.domain.util.weatherInfo
import com.enjot.materialweather.fakes.SavedLocationDaoFake
import com.enjot.materialweather.fakes.WeatherDaoFake
import com.enjot.materialweather.weather.data.database.LocalRepositoryImpl
import com.enjot.materialweather.weather.domain.model.WeatherInfo
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocalRepositoryImplTest {
    
    private lateinit var weatherDao: WeatherDaoFake
    private lateinit var savedLocationDao: SavedLocationDaoFake
    private lateinit var repository: LocalRepository
    
    @BeforeEach
    fun setUp() {
        weatherDao = WeatherDaoFake()
        savedLocationDao = SavedLocationDaoFake()
        repository = LocalRepositoryImpl(savedLocationDao, weatherDao)
    }
    
    @Test
    fun `Save weather, flow emits new value`() = runBlocking {
        
        val initial = repository.getLocalWeather().first()
        assertThat(initial.place).isNull()
        
        val weatherInfo = weatherInfo().copy(
            place = place().copy(city = "London")
        )
        
        repository.saveLocalWeather(weatherInfo)
        
        val result = repository.getLocalWeather().first()
        
        assertThat(result.place!!.city).isEqualTo(weatherInfo.place!!.city)
    }
    
    @Test
    fun `Add saved location, flow emits new value`() = runTest {
        
        val savedLocation = savedLocation().copy(id = 50)
        
        repository.addSavedLocation(savedLocation)
        
        val result = repository.getSavedLocations().first().find { it.id == 50 }
        
        assertThat(result).isEqualTo(savedLocation)
    }
    
    @Test
    fun `Query for weather, empty table, return default value`() = runTest {
        
        weatherDao.isTableEmpty = true
        
        val result = repository.getLocalWeather().first()
        val defaultWeatherInfo = WeatherInfo.empty()
        
        assertThat(result).isEqualTo(defaultWeatherInfo)
    }
    
    @Test
    fun `Query for saved locations, empty table, return empty list`() = runTest {
        
        val result = repository.getSavedLocations().first()
        
        assertThat(result).isEqualTo(emptyList())
    }
    
    @Test
    fun `Save 2 locations, delete first one, return list containing second one`() = runTest {
        
        val savedLocation1 = savedLocation().copy(id = 1)
        val savedLocation2 = savedLocation().copy(id = 2)
        
        repository.addSavedLocation(savedLocation1)
        repository.addSavedLocation(savedLocation2)
        repository.deleteSavedLocation(savedLocation1)
        
        val result = repository.getSavedLocations().first()
        
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].id).isEqualTo(2)
    }
}
