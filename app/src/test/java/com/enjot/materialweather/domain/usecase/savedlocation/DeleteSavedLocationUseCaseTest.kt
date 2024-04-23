package com.enjot.materialweather.domain.usecase.savedlocation

import assertk.assertThat
import assertk.assertions.isEmpty
import com.enjot.materialweather.domain.util.savedLocation
import com.enjot.materialweather.fakes.LocalRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteSavedLocationUseCaseTest {

    private lateinit var localRepository: LocalRepositoryFake
    private lateinit var deleteSavedLocationUseCase: DeleteSavedLocationUseCase

    @BeforeEach
    fun setUp() {
        localRepository = LocalRepositoryFake()
        deleteSavedLocationUseCase = DeleteSavedLocationUseCase(localRepository)
    }

    @Test
    fun `Delete location, deleted correctly`() = runTest {

        val savedLocation = savedLocation()

        localRepository.savedLocations.value.add(savedLocation)

        deleteSavedLocationUseCase(savedLocation)

        assertThat(localRepository.savedLocations.value).isEmpty()
    }
}