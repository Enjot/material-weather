package com.enjot.materialweather.fakes

import com.enjot.materialweather.domain.model.UserPreferences
import com.enjot.materialweather.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class PreferencesRepositoryFake : PreferencesRepository {

    val userPreferences = MutableStateFlow(UserPreferences(30))

    override fun getUserPreferencesFlow(): Flow<UserPreferences> {
        return userPreferences
    }

    override suspend fun setBackgroundUpdatesRepeatInterval(repeatInterval: Long) {
        userPreferences.update { it.copy(backgroundUpdatesRepeatInterval = repeatInterval) }
    }

}