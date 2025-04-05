package com.enjot.materialweather.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.enjot.materialweather.domain.model.UserPreferences
import com.enjot.materialweather.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

private const val TAG = "UserPreferencesRepo"

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    private object PreferencesKeys {
        val BACKGROUND_UPDATES_REPEAT_INTERVAL =
            longPreferencesKey("background_updates_repeat_interval")
    }

    override fun getUserPreferencesFlow(): Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.tag(TAG).e(exception, "Error reading preferences.")
            } else {
                throw exception
            }
        }
        .map { preferences ->
            mapUserPreferences(preferences)
        }

    override suspend fun setBackgroundUpdatesRepeatInterval(repeatInterval: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BACKGROUND_UPDATES_REPEAT_INTERVAL] = repeatInterval
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {

        val intervalRepeat = preferences[PreferencesKeys.BACKGROUND_UPDATES_REPEAT_INTERVAL] ?: 30L

        return UserPreferences(intervalRepeat)
    }
}
