package com.enjot.materialweather.domain.usecase.savedlocation

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedLocationsFlow @Inject constructor(
    private val localRepository: LocalRepository
) {
    operator fun invoke(): Flow<List<SavedLocation>> = localRepository.getSavedLocations()
}