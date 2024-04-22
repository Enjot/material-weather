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
import com.enjot.materialweather.domain.repository.DispatcherProvider
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.utils.ErrorType
import com.enjot.materialweather.domain.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

const val TAG = "REMOTE_REPOSITORY"

class RemoteRepositoryImpl @Inject constructor(
    @Named("openweathermap") private val openWeatherMapApi: OpenWeatherMapApi,
    @Named("geoapify") private val geoapifyApi: GeoapifyApi,
    private val dispatcherProvider: DispatcherProvider
) : RemoteRepository {
    
    override suspend fun fetchWeather(coordinates: Coordinates): Resource<WeatherInfo> {
        return withContext(dispatcherProvider.io) {
            
            try {
                val reverseResponse = geoapifyApi.callReverseGeocodingApi(
                    coordinates.lat.toString(),
                    coordinates.lon.toString()
                )
                
                if (!reverseResponse.isSuccessful || reverseResponse.body() == null)
                    return@withContext Resource.Error(ErrorType.HTTP)
                
                val place = reverseResponse.body()!!.results.getOrNull(0)
                    ?: return@withContext Resource.Error(ErrorType.HTTP)
                
                val weatherDeferred = async {
                    openWeatherMapApi
                        .callOneCallApi(coordinates.lat.toString(), coordinates.lon.toString())
                }
                
                val airPollutionDeferred = async {
                    openWeatherMapApi
                        .callAirPollutionApi(coordinates.lat.toString(), coordinates.lon.toString())
                }
                
                val oneCallResponse = weatherDeferred.await()
                val airPollutionResponse = airPollutionDeferred.await()
                
                if (!oneCallResponse.isSuccessful)
                    return@withContext Resource.Error(ErrorType.HTTP)
                
                val airPollution =
                    if (airPollutionResponse.isSuccessful)
                        airPollutionResponse.body()?.toDomainAirPollutionOrNull()
                    else null
                
                val weatherInfo = WeatherInfo(
                    place = place.toDomainSearchResult(),
                    current = oneCallResponse.body()?.toDomainCurrentWeather(),
                    hourly = oneCallResponse.body()?.toDomainHourlyWeatherList(),
                    daily = oneCallResponse.body()?.toDomainDailyWeatherList(),
                    airPollution = airPollution
                )
                
                return@withContext Resource.Success(weatherInfo)
            } catch (e: HttpException) {
                return@withContext Resource.Error(ErrorType.HTTP)
            } catch (e: IOException) {
                return@withContext Resource.Error(ErrorType.NETWORK)
            } catch (e: Exception) {
                return@withContext Resource.Error(ErrorType.UNKNOWN)
            } catch (e: Throwable) {
                return@withContext Resource.Error(ErrorType.UNKNOWN)
            }
        }
    }
    
    override suspend fun getSearchResults(query: String): Resource<List<SearchResult>> {
        
        return withContext(dispatcherProvider.io) {
            try {
                
                val geocodingResponse = geoapifyApi.callGeocodingApi(query)
                
                if (!geocodingResponse.isSuccessful)
                    return@withContext Resource.Error(ErrorType.HTTP)
                
                val geocodingResults = geocodingResponse.body()?.results ?: emptyList()
                
                val deferredResults = geocodingResults.map { result ->
                    async { getReverseGeocodingResult(result.lat, result.lon) }
                }
                
                val reverseResponse = deferredResults.awaitAll().filterNotNull()
                
                val searchResults = reverseResponse
                    .map { it.toDomainSearchResult() }
                    .filterDuplicatesKeepNulls { it.postCode }
                
                if (searchResults.isEmpty())
                    return@withContext Resource.Error(ErrorType.NO_RESULTS)
                
                return@withContext Resource.Success(searchResults)
            } catch (e: HttpException) {
                return@withContext Resource.Error(ErrorType.HTTP)
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
            
            if (!response.isSuccessful)
                throw HttpException(
                    Response.error<ResponseBody>(
                        500,
                        "Unknown error".toResponseBody("plain/text".toMediaTypeOrNull())
                    )
                )
            return response.body()?.results?.getOrNull(0)
        } catch (e: IOException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}
