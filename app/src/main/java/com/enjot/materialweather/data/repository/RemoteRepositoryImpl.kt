package com.enjot.materialweather.data.repository

import android.util.Log
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
    @Named("geoapify") private val geoapifyApi: GeoapifyApi
) : RemoteRepository {
    
    override suspend fun fetchWeather(coordinates: Coordinates): Resource<WeatherInfo> {
        Log.d(TAG, "fetchWeather starts executing")
        return withContext(Dispatchers.IO) {
            
            try {
                Log.d(TAG, "callReverseGeocodingApi starts executing")
                val reverseResponse = geoapifyApi.callReverseGeocodingApi(
                    coordinates.lat.toString(),
                    coordinates.lon.toString()
                )
                val place = if (reverseResponse.isSuccessful)
                    reverseResponse.body()?.results?.get(0)
                        ?: return@withContext Resource.Error(ErrorType.HTTP)
                else return@withContext Resource.Error(ErrorType.HTTP)
                
                Log.d(TAG, "callOneCallApi starts executing")
                val weatherDeferred = async {
                    openWeatherMapApi.callOneCallApi(
                        coordinates.lat.toString(),
                        coordinates.lon.toString()
                    )
                }
                Log.d(TAG, "callAirPollutionApi starts executing")
                val airPollutionDeferred = async {
                    openWeatherMapApi.callAirPollutionApi(
                        coordinates.lat.toString(),
                        coordinates.lon.toString()
                    )
                }
                
                val oneCallResponse = weatherDeferred.await()
                Log.d(TAG, "callOneCallApi executed")
                val airPollutionResponse = airPollutionDeferred.await()
                Log.d(TAG, "callAirPollutionApi executed")
                
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
                Log.d(TAG, "weatherInfo created")
                
                return@withContext Resource.Success(weatherInfo)
            } catch (e: IOException) {
                Log.e(TAG, "Caught IOException", e)
                return@withContext Resource.Error(ErrorType.NETWORK)
            } catch (e: Exception) {
                Log.e(TAG, "Caught Exception", e)
                return@withContext Resource.Error(ErrorType.UNKNOWN)
            } catch (e: Throwable) {
                Log.e(TAG, "Caught Throwable", e)
                return@withContext Resource.Error(ErrorType.UNKNOWN)
            }
        }
    }
    
    override suspend fun getSearchResults(query: String): Resource<List<SearchResult>> {
        
        return withContext(Dispatchers.IO) {
            try {
                
                val geocodingResponse = geoapifyApi.callGeocodingApi(query)
                
                val geocodingResults = if (geocodingResponse.isSuccessful)
                    geocodingResponse.body()?.results ?: emptyList()
                else return@withContext Resource.Error(ErrorType.HTTP)
                
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
            
            val results = if (response.isSuccessful) {
                response.body()?.results ?: emptyList()
            } else throw HttpException(
                Response.error<ResponseBody>(
                    500,
                    "Unknown error".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
            return if (results.isNotEmpty()) results[0] else null
        } catch (e: IOException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}
