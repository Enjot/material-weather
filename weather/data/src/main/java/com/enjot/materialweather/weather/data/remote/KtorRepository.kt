package com.enjot.materialweather.weather.data.remote

import com.enjot.materialweather.core.domain.Coordinates
import com.enjot.materialweather.core.domain.DispatcherProvider
import com.enjot.materialweather.core.domain.error.DataError
import com.enjot.materialweather.core.domain.utils.ErrorType
import com.enjot.materialweather.core.domain.utils.Resource
import com.enjot.materialweather.core.domain.utils.Result
import com.enjot.materialweather.weather.data.mapper.toDomainAirPollutionOrNull
import com.enjot.materialweather.weather.data.mapper.toDomainCurrentWeather
import com.enjot.materialweather.weather.data.mapper.toDomainDailyWeatherList
import com.enjot.materialweather.weather.data.mapper.toDomainHourlyWeatherList
import com.enjot.materialweather.weather.data.mapper.toDomainSearchResult
import com.enjot.materialweather.weather.data.remote.dto.AirPollutionDto
import com.enjot.materialweather.weather.data.remote.dto.GeocodingDto
import com.enjot.materialweather.weather.data.remote.dto.OneCallDto
import com.enjot.materialweather.weather.data.remote.dto.ReverseGeocodingDto
import com.enjot.materialweather.weather.domain.model.SearchResult
import com.enjot.materialweather.weather.domain.model.WeatherInfo
import com.enjot.materialweather.weather.domain.repository.RemoteRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import pl.audiotocom.enjot.materialweather.core.data.remote.get
import timber.log.Timber

class KtorRepository(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider
): RemoteRepository {

    override suspend fun fetchWeather(coordinates: Coordinates): Resource<WeatherInfo> {

        Timber.tag(TAG).d("fetchWeather()")
        return withContext(dispatcherProvider.io) {
            try {
                val reverseResult = httpClient.get<ReverseGeocodingDto>(
                    route = HttpRoutes.REVERSED_GEOCODING,
                    queryParameters = mapOf(
                        "lat" to coordinates.lat.toString(),
                        "lon" to coordinates.lon.toString()
                    )
                )

                if (reverseResult is Result.Error) {
                    if (reverseResult.error == DataError.Network.SERVER_ERROR)
                        return@withContext Resource.Error(ErrorType.HTTP)
                    else return@withContext Resource.Error(ErrorType.UNKNOWN)
                }

                val place = (reverseResult as Result.Success).data.results.getOrNull(0)
                    ?: return@withContext Resource.Error(ErrorType.HTTP)

                val weatherDeferred = async {
                    httpClient.get<OneCallDto>(
                        route = HttpRoutes.ONE_CALL_URL,
                        queryParameters = mapOf(
                            "lat" to coordinates.lat.toString(),
                            "lon" to coordinates.lon.toString(),
                            "lang" to "eng",
                            "units" to "metric",
                            "exclude" to "minutely,alerts"
                        )
                    )
                }

                val airPollutionDeferred = async {
                    httpClient.get<AirPollutionDto>(
                        route = HttpRoutes.AIR_POLLUTION_URL,
                        queryParameters = mapOf(
                            "lat" to coordinates.lat.toString(),
                            "lon" to coordinates.lon.toString()
                        )
                    )
                }

                val oneCallResult = weatherDeferred.await()
                val airPollutionResult = airPollutionDeferred.await()

                if (oneCallResult is Result.Error) return@withContext Resource.Error(ErrorType.HTTP)

                val airPollution = if (airPollutionResult is Result.Success) {
                    airPollutionResult.data.toDomainAirPollutionOrNull()
                } else null

                val weatherInfo = WeatherInfo(
                    place = place.toDomainSearchResult(),
                    current = (oneCallResult as Result.Success).data.toDomainCurrentWeather(),
                    hourly = oneCallResult.data.toDomainHourlyWeatherList().subList(0, 24),
                    daily = oneCallResult.data.toDomainDailyWeatherList(),
                    airPollution = airPollution
                )

                return@withContext Resource.Success(weatherInfo)
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

                val geocodingResponse = httpClient.get<GeocodingDto>(
                    route = HttpRoutes.GEOCODING,
                    queryParameters = mapOf(
                        "text" to query
                    )
                )

                when (geocodingResponse) {
                    is Result.Error -> return@withContext Resource.Error(ErrorType.HTTP)
                    is Result.Success -> {
                        val geocodingResults = geocodingResponse.data.results
                        val deferredResults = geocodingResults.map { result ->
                            async { getReverseGeocodingResult(result.lat, result.lon) }
                        }
                        val reverseResponse = deferredResults.awaitAll().filterNotNull()

                        val searchResults = reverseResponse
                            .map { it.toDomainSearchResult() }
                            .filterDuplicatesKeepNulls { it.postCode }

                        if (searchResults.isEmpty()) return@withContext Resource.Error(ErrorType.NO_RESULTS)
                        else return@withContext Resource.Success(searchResults)
                    }
                }
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
            val response = httpClient.get<ReverseGeocodingDto>(
                route = HttpRoutes.REVERSED_GEOCODING,
                queryParameters = mapOf(
                    "lat" to lat.toString(),
                    "lon" to lon.toString()
                )
            )
            if (response is Result.Error)
                throw Exception()

            return (response as Result.Success).data.results.getOrNull(0)
        } catch (e: IOException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    companion object {
        private const val TAG = "KtorRepository"
    }
}