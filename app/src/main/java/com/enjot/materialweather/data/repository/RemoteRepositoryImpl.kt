package com.enjot.materialweather.data.repository

import com.enjot.materialweather.data.mapper.toDomainAirPollutionOrNull
import com.enjot.materialweather.data.mapper.toDomainCurrentWeather
import com.enjot.materialweather.data.mapper.toDomainDailyWeatherList
import com.enjot.materialweather.data.mapper.toDomainHourlyWeatherList
import com.enjot.materialweather.data.mapper.toDomainSearchResult
import com.enjot.materialweather.data.remote.openweathermap.api.GeoapifyApi
import com.enjot.materialweather.data.remote.openweathermap.api.OpenWeatherMapApi
import com.enjot.materialweather.data.remote.openweathermap.dto.ReverseGeocodingDto
import com.enjot.materialweather.domain.model.Coordinates
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.model.WeatherInfo
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.utils.ErrorType
import com.enjot.materialweather.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class RemoteRepositoryImpl @Inject constructor(
    @Named("openweathermap") private val openWeatherMapApi: OpenWeatherMapApi,
    @Named("geoapify") private val geoapifyApi: GeoapifyApi
) : RemoteRepository {
    
    override suspend fun fetchWeather(coordinates: Coordinates): Resource<WeatherInfo> {
        
        return withContext(Dispatchers.IO) {
            
            try {
                val place = geoapifyApi.callReverseGeocodingApi(
                    coordinates.lat.toString(),
                    coordinates.lon.toString()
                ).results[0]
                
                val weatherDeferred = async {
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
                
                val weather = weatherDeferred.await()
                val airPollution = airPollutionDeferred.await()
                
                val weatherInfo = WeatherInfo(
                    place = place.toDomainSearchResult(),
                    current = weather.toDomainCurrentWeather(),
                    hourly = weather.toDomainHourlyWeatherList(),
                    daily = weather.toDomainDailyWeatherList(),
                    airPollution = airPollution.toDomainAirPollutionOrNull()
                )
                
                return@withContext Resource.Success(weatherInfo)
            } catch (e: HttpException) {
                return@withContext Resource.Error(ErrorType.SERVER)
            } catch (e: IOException) {
                return@withContext Resource.Error(ErrorType.NETWORK)
            } catch (e: Exception) {
                return@withContext Resource.Error(ErrorType.UNKNOWN)
            }
        }
    }
    
    override suspend fun getSearchResults(query: String): Resource<List<SearchResult>> {
        
        return withContext(Dispatchers.IO) {
            try {
                val geocodingResponse = geoapifyApi.callGeocodingApi(query).results

                val deferredResults = geocodingResponse.map { result ->
                    async { getReverseGeocodingResult(result.lat, result.lon) }
                }
                
                val reverseGeocodingResponse = deferredResults.awaitAll().filterNotNull()

                val searchResults = reverseGeocodingResponse
                    .map { it.toDomainSearchResult() }
                    .filterDuplicatesKeepNulls { it.postCode }

                return@withContext Resource.Success(searchResults)
            } catch (e: HttpException) {
                return@withContext Resource.Error(ErrorType.SERVER)
            } catch (e: IOException) {
                return@withContext Resource.Error(ErrorType.NETWORK)
            } catch (e: Exception) {
                return@withContext Resource.Error(ErrorType.UNKNOWN)
            }
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
    
    private suspend fun getReverseGeocodingResult(
        lat: Double,
        lon: Double
    ): ReverseGeocodingDto.Result? {
        try {
            val response = geoapifyApi.callReverseGeocodingApi(lat.toString(), lon.toString())
            return if (response.results.isNotEmpty()) {
                response.results[0]
            } else null
            
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}
