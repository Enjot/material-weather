package com.enjot.materialweather.domain.repository

import com.enjot.materialweather.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    
    fun getUserPreferencesFlow(): Flow<UserPreferences>
    
    suspend fun setBackgroundUpdatesRepeatInterval(repeatInterval: Long)
    
}