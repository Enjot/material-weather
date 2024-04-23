package com.enjot.materialweather.domain.usecase.weather

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.enjot.materialweather.domain.repository.LocalRepository
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.util.coordinates
import com.enjot.materialweather.domain.util.weatherInfo
import com.enjot.materialweather.domain.utils.ErrorType
import com.enjot.materialweather.domain.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateWeatherUseCaseTest {

    private lateinit var localRepository: LocalRepository
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var updateWeatherUseCase: UpdateWeatherUseCase

    @BeforeEach
    fun setUp() {
        localRepository = mockk(relaxed = true)
        remoteRepository = mockk(relaxed = true)
        updateWeatherUseCase = UpdateWeatherUseCase(
            remoteRepository,
            localRepository
        )
    }

    @Test
    fun `Update weather, returns resource success`() = runTest {

        coEvery { remoteRepository.fetchWeather(any()) } returns Resource.Success(weatherInfo())
        coEvery { localRepository.saveLocalWeather(any()) } returns Unit

        val result = updateWeatherUseCase.invoke(coordinates())

        assertThat(result).isInstanceOf(Resource.Success::class)

        coVerify {
            remoteRepository.fetchWeather(any())
            localRepository.saveLocalWeather(any())
        }
        confirmVerified(remoteRepository, localRepository)
    }

    @Test
    fun `Remote repo returns success but empty body, it isn't saved by local repo`() = runTest {

        coEvery { remoteRepository.fetchWeather(any()) } returns Resource.Success()

        val result = updateWeatherUseCase(coordinates())

        assertThat(result.errorType).isEqualTo(ErrorType.UNKNOWN)

        coVerify(exactly = 0) { localRepository.saveLocalWeather(any()) }

        confirmVerified(localRepository)
    }

    @Test
    fun `Remote repo returns error, correct error type returned`() = runTest {

        coEvery { remoteRepository.fetchWeather(any()) } returns
                Resource.Error(ErrorType.NETWORK)

        val result = updateWeatherUseCase(coordinates())

        assertThat(result.errorType).isEqualTo(ErrorType.NETWORK)

        coVerify(exactly = 0) { localRepository.saveLocalWeather(any()) }

        confirmVerified(localRepository)
    }
}