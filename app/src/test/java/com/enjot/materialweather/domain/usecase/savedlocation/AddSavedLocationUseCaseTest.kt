package com.enjot.materialweather.domain.usecase.savedlocation

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import com.enjot.materialweather.domain.util.searchResult
import com.enjot.materialweather.fakes.LocalRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddSavedLocationUseCaseTest {

    private lateinit var localRepository: LocalRepositoryFake
    private lateinit var addSavedLocationUseCase: AddSavedLocationUseCase

    @BeforeEach
    fun setUp() {
        localRepository = LocalRepositoryFake()
        addSavedLocationUseCase = AddSavedLocationUseCase(localRepository)
    }

    @Test
    fun `Save location, saved correctly`() = runTest {

        addSavedLocationUseCase(searchResult())

        assertThat(localRepository.savedLocations.value).isNotEmpty()
    }

    @Test
    fun `Save location, converted correctly`() = runTest {

        val searchResult = searchResult()

        addSavedLocationUseCase(searchResult)

        val savedLocation = localRepository.savedLocations.value[0]

        assertThat(searchResult.city).isEqualTo(savedLocation.name)
        assertThat(searchResult.postCode).isEqualTo(savedLocation.postCode)
        assertThat(searchResult.countryCode).isEqualTo(savedLocation.countryCode)
        assertThat(searchResult.coordinates).isEqualTo(savedLocation.coordinates)
    }
}