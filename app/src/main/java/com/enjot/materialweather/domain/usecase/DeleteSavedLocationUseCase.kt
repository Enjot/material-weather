package com.enjot.materialweather.domain.usecase

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.repository.LocalRepository
import javax.inject.Inject

class DeleteSavedLocationUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(savedLocation: SavedLocation) {
        localRepository.deleteSavedLocation(savedLocation)
    }
}