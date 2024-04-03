package com.enjot.materialweather.domain.usecase

import com.enjot.materialweather.domain.model.SavedLocation
import com.enjot.materialweather.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedLocationsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): Flow<List<SavedLocation>> = localRepository.getSavedLocations()
}