package com.enjot.materialweather.weather.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.enjot.materialweather.core.domain.LocationRepository
import com.enjot.materialweather.core.domain.utils.ErrorType
import com.enjot.materialweather.core.domain.utils.Resource
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import com.enjot.materialweather.weather.domain.repository.RemoteRepository
import com.enjot.materialweather.weather.domain.usecase.GetWeatherFromLocationUseCase
import com.enjot.materialweather.weather.domain.util.coordinates
import com.enjot.materialweather.weather.domain.util.weatherInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetWeatherFromLocationUseCaseTest {

    private lateinit var remoteRepository: RemoteRepository
    private lateinit var locationRepository: LocationRepository
    private lateinit var localRepository: LocalRepository
    private lateinit var getWeatherFromLocationUseCase: GetWeatherFromLocationUseCase

    @BeforeEach
    fun setUp() {
        remoteRepository = mockk(relaxed = true)
        locationRepository = mockk(relaxed = true)
        localRepository = mockk(relaxed = true)
        getWeatherFromLocationUseCase = GetWeatherFromLocationUseCase(
            remoteRepository,
            locationRepository,
            localRepository
        )
    }

    @Test
    fun `Location repo returns resource error, correct error type returned`() = runTest {

        coEvery { locationRepository.getCoordinates() } returns Resource.Error(ErrorType.LOCATION)

        val result = getWeatherFromLocationUseCase()

        coVerify(exactly = 0) { localRepository.saveLocalWeather(any()) }
        confirmVerified(localRepository)

        assertThat(result.errorType).isEqualTo(ErrorType.LOCATION)
    }

    @Test
    fun `Location repo returns empty body, correct error type returned`() = runTest {

        coEvery { locationRepository.getCoordinates() } returns Resource.Success()

        val result = getWeatherFromLocationUseCase()

        coVerify(exactly = 0) { localRepository.saveLocalWeather(any()) }
        confirmVerified(localRepository)

        assertThat(result.errorType).isEqualTo(ErrorType.LOCATION)
    }

    @Test
    fun `Location repo returns success, network returns error, correct error type returned`() =
        runTest {

            coEvery { locationRepository.getCoordinates() } returns Resource.Success(coordinates())
            coEvery { remoteRepository.fetchWeather(any()) } returns Resource.Error(ErrorType.NETWORK)

            val result = getWeatherFromLocationUseCase()

            coVerify(exactly = 0) { localRepository.saveLocalWeather(any()) }
            confirmVerified(localRepository)

            assertThat(result.errorType).isEqualTo(ErrorType.NETWORK)
        }

    @Test
    fun `Location repo returns success, network returns empty body, correct error type returned`() =
        runTest {

            coEvery { locationRepository.getCoordinates() } returns Resource.Success(coordinates())
            coEvery { remoteRepository.fetchWeather(any()) } returns Resource.Success()

            val result = getWeatherFromLocationUseCase()

            coVerify(exactly = 0) { localRepository.saveLocalWeather(any()) }
            confirmVerified(localRepository)

            assertThat(result.errorType).isEqualTo(ErrorType.UNKNOWN)
        }

    @Test
    fun `Location repo returns success, network returns success, correct error type returned`() =
        runTest {

            coEvery { locationRepository.getCoordinates() } returns Resource.Success(coordinates())
            coEvery { remoteRepository.fetchWeather(any()) } returns Resource.Success(weatherInfo())

            getWeatherFromLocationUseCase()

            coVerify(exactly = 1) { localRepository.saveLocalWeather(any()) }
            confirmVerified(localRepository)

        }
}