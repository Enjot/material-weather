package com.enjot.materialweather.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.enjot.materialweather.data.remote.openweathermap.api.GeoapifyApi
import com.enjot.materialweather.data.remote.openweathermap.api.OpenWeatherMapApi
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.util.MainCoroutineExtension
import com.enjot.materialweather.domain.util.TestDispatchers
import com.enjot.materialweather.domain.util.coordinates
import com.enjot.materialweather.domain.utils.ErrorType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import retrofit2.HttpException
import retrofit2.Response

class RemoteRepositoryImplTest {
    
    private lateinit var repository: RemoteRepository
    private lateinit var geoapifyApi: GeoapifyApi
    private lateinit var openWeatherMapApi: OpenWeatherMapApi
    
    
    companion object {
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }
    
    @BeforeEach
    fun setUp() {
        
        geoapifyApi = mockk(relaxed = true)
        openWeatherMapApi = mockk(relaxed = true)
        
        val testDispatchers = TestDispatchers(mainCoroutineExtension.testDispatcher)
        
        repository = RemoteRepositoryImpl(
            geoapifyApi = geoapifyApi,
            openWeatherMapApi = openWeatherMapApi,
            dispatcherProvider = testDispatchers
        )
    }
    
    @Test
    fun `Fetch weather, geoapify throws HTTPException, return HTTP error type`() = runTest {
        
        coEvery { geoapifyApi.callReverseGeocodingApi(any(), any()) } throws HttpException(
            Response.error<ResponseBody>(
                500, "Unknown error".toResponseBody("plain/text".toMediaTypeOrNull())
            )
        )
        
        val result = repository.fetchWeather(coordinates())
        
        assertThat(result.errorType).isEqualTo(ErrorType.HTTP)
    }
    
}