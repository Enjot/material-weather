package com.enjot.materialweather.data.repository

import com.enjot.materialweather.data.database.weather.WeatherDao
import com.enjot.materialweather.data.database.weather.WeatherEntity
import com.enjot.materialweather.data.mapper.toAirPollutionOrNull
import com.enjot.materialweather.data.mapper.toCurrentWeather
import com.enjot.materialweather.data.mapper.toDailyWeatherList
import com.enjot.materialweather.data.mapper.toDomainSearchResult
import com.enjot.materialweather.data.mapper.toHourlyWeatherList
import com.enjot.materialweather.data.mapper.toLocalSearchResult
import com.enjot.materialweather.data.mapper.toWeatherInfo
import com.enjot.materialweather.data.remote.openweathermap.api.GeoapifyApi
import com.enjot.materialweather.data.remote.openweathermap.api.OpenWeatherMapApi
import com.enjot.materialweather.data.remote.openweathermap.dto.ReverseGeocodingDto
import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class RemoteRepositoryImpl @Inject constructor(
    @Named("openweathermap") private val openWeatherMapApi: OpenWeatherMapApi,
    @Named("geoapify") private val geoapifyApi: GeoapifyApi,
    private val dao: WeatherDao
) : RemoteRepository {
    
    override suspend fun getWeather(coordinates: Coordinates): Resource<WeatherInfo?> {
        
        return withContext(Dispatchers.IO) {
            
            val searchResult = geoapifyApi.callReverseGeocodingApi(
                coordinates.lat.toString(),
                coordinates.lon.toString()
            ).results[0]
            
            val oneCallDeferred = async {
                openWeatherMapApi.callOneCallApi(
                    coordinates.lat.toString(),
                    coordinates.lon.toString()
                )
            }
            
            val airPollutionDeferred = async {
                openWeatherMapApi.callAirPollutionApi(
                    coordinates.lat.toString(),
                    coordinates.lon.toString()
                )
            }
            
            val oneCallDto = oneCallDeferred.await()
            val airPollutionDto = airPollutionDeferred.await()
            
            val weatherEntity = WeatherEntity(
                place = searchResult.toLocalSearchResult(),
                current = oneCallDto.toCurrentWeather(),
                hourly = oneCallDto.toHourlyWeatherList(),
                daily = oneCallDto.toDailyWeatherList(),
                airPollution = airPollutionDto.toAirPollutionOrNull()
            )
            dao.insertWeather(weatherEntity)
            val weather = dao.getWeather().first()?.toWeatherInfo()
            Resource.Success(weather)
        }
    }

    suspend fun loadLocalWeather(): Resource<WeatherInfo?> {
        val weather = dao.getWeather().first()?.toWeatherInfo()
        return Resource.Success(weather)
    }
    
    override suspend fun getSearchResults(query: String): Resource<List<SearchResult>> {
        
        val geocodingResults = geoapifyApi.callGeocodingApi(query).results
        
        return withContext(Dispatchers.IO) {
            
            val deferredResults = geocodingResults.map { result ->
                async { getReverseGeocodingResult(result.lat, result.lon) }
            }
            
            val reverseGeocodingResults =
                deferredResults.awaitAll().filterNotNull()
            
            val results = reverseGeocodingResults
                .map { it.toDomainSearchResult() }
                .filterDuplicatesKeepNulls { it.postCode }
            
            Resource.Success(results)
        }
    }
    
    private fun <T> List<SearchResult>.filterDuplicatesKeepNulls(
        selector: (SearchResult) -> T?
    ): List<SearchResult> {
        return this
            .groupBy(selector)
            .flatMap { entry ->
                if (entry.key == null) entry.value
                else listOf(entry.value.first())
            }
    }
    
    /*
    Api returns list of results even if I limit amount of them to 1
    so I always need item with index 0
    */
    
    private suspend fun getReverseGeocodingResult(
        lat: Double,
        lon: Double
    ): ReverseGeocodingDto.Result? {
        val response = geoapifyApi.callReverseGeocodingApi(
            lat.toString(), lon.toString()
        )
        return if (response.results.isNotEmpty()) {
            response.results[0]
        } else null
    }
}

