package com.enjot.materialweather.data.repository

import com.enjot.materialweather.data.remote.openweathermap.api.GeoapifyApi
import com.enjot.materialweather.data.remote.openweathermap.api.OpenWeatherMapApi
import com.enjot.materialweather.domain.repository.RemoteRepository
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach

class RemoteRepositoryImplTest {
    
    private lateinit var repository: RemoteRepository
    private lateinit var geoapifyApi: GeoapifyApi
    private lateinit var openWeatherMapApi: OpenWeatherMapApi
    
    @BeforeEach
    fun setUp() {
        geoapifyApi = mockk(relaxed = true)
        openWeatherMapApi = mockk(relaxed = true)
        repository = RemoteRepositoryImpl(
            geoapifyApi = geoapifyApi,
            openWeatherMapApi = openWeatherMapApi
        )
    }
    
    
}