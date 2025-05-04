package com.enjot.materialweather.weather.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.enjot.materialweather.core.domain.utils.ErrorType
import com.enjot.materialweather.weather.data.remote.KtorRepository
import com.enjot.materialweather.weather.data.util.MainCoroutineExtension
import com.enjot.materialweather.weather.data.util.TestDispatchers
import com.enjot.materialweather.weather.data.util.coordinates
import com.enjot.materialweather.weather.domain.repository.RemoteRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import pl.audiotocom.enjot.materialweather.core.data.remote.HttpClientFactory

class RemoteRepositoryImplTest {

    private lateinit var repository: RemoteRepository
    private lateinit var httpClient: HttpClient

    companion object {
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    @BeforeEach
    fun setUp() {

        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        httpClient = HttpClientFactory.build(mockEngine)

        val testDispatchers = TestDispatchers(mainCoroutineExtension.testDispatcher)

        repository = KtorRepository(
            httpClient = httpClient,
            dispatcherProvider = testDispatchers
        )
    }

    @Test
    fun `Fetch weather, call returns SERVER_ERROR, return HTTP error type`() = runTest {

        val result = repository.fetchWeather(coordinates())

        assertThat(result.errorType).isEqualTo(ErrorType.HTTP)
    }
}