package com.enjot.materialweather.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.enjot.materialweather.data.remote.openweathermap.api.GeoapifyApi
import com.enjot.materialweather.data.remote.openweathermap.api.OpenWeatherMapApi
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.util.coordinates
import com.enjot.materialweather.domain.utils.ErrorType
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.create

class RemoteRepositoryImplTest {
    
    private lateinit var repository: RemoteRepository
    private lateinit var geoapifyApi: GeoapifyApi
    private lateinit var openWeatherMapApi: OpenWeatherMapApi
    
    private lateinit var geoapifyServer: MockWebServer
    private lateinit var openWeatherMapServer: MockWebServer
    
    @BeforeEach
    fun setUp() {
        geoapifyServer = MockWebServer()
        openWeatherMapServer = MockWebServer()
        
        geoapifyApi = Retrofit.Builder()
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType())
            )
            .baseUrl(geoapifyServer.url("/"))
            .build()
            .create()
        
        openWeatherMapApi = Retrofit.Builder()
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType())
            )
            .baseUrl(openWeatherMapServer.url("/"))
            .build()
            .create()
        
        repository = RemoteRepositoryImpl(
            geoapifyApi = geoapifyApi,
            openWeatherMapApi = openWeatherMapApi
        )
    }
    
    @Test
    fun `Fetch weather, geoapify throws HTTPException, handle it correctly`() = runTest {
        
        geoapifyServer.enqueue(
            MockResponse().setResponseCode(404)
        )
        
        val result = repository.fetchWeather(coordinates())
        
        assertThat(result.errorType).isEqualTo(ErrorType.HTTP)
    }
    
}