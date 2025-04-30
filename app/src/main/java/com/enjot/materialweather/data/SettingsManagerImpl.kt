package com.enjot.materialweather.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.enjot.materialweather.data.background.UPDATE_WEATHER_WORK
import com.enjot.materialweather.data.background.UpdateWeatherWorker
import com.enjot.materialweather.data.util.isEnqueuedOrRunning
import com.enjot.materialweather.domain.model.Settings
import com.enjot.materialweather.domain.repository.SettingsManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

class SettingsManagerImpl(
    private val workManager: WorkManager,
    private val dataStore: DataStore<Preferences>
) : SettingsManager {

    companion object {
        private const val TAG = "SettingsManagerImpl"
        private const val MIN_INTERVAL = 15L
        const val DEFAULT_BACKGROUND_UPDATES_REPEAT_INTERVAL = 30L
        val BACKGROUND_UPDATES_REPEAT_INTERVAL = longPreferencesKey("background_updates_repeat_interval")
    }

    private val _areBackgroundUpdatesEnabled = workManager.getWorkInfosForUniqueWorkFlow(
        UPDATE_WEATHER_WORK
    ).map { workInfos ->
        workInfos.any { it.state.isEnqueuedOrRunning() }
    }

    private val _backgroundUpdatesRepeatInterval = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.tag(TAG).e(exception, "Error reading preferences, emitting defaults.")
                emit(emptyPreferences())
            } else throw exception
        }
        .map { preferences ->
            preferences[BACKGROUND_UPDATES_REPEAT_INTERVAL]
                ?: DEFAULT_BACKGROUND_UPDATES_REPEAT_INTERVAL
        }

    override fun settingsStateFlow() = combine(
        _areBackgroundUpdatesEnabled,
        _backgroundUpdatesRepeatInterval
    ) { areBackgroundUpdatesEnabled, backgroundUpdatesRepeatInterval ->
        Settings(
            backgroundUpdatesEnabled = areBackgroundUpdatesEnabled,
            backgroundUpdatesRepeatInterval = backgroundUpdatesRepeatInterval,
            areLocationBasedBackgroundUpdatesEnabled = false
        )
    }

    override suspend fun toggleBackgroundUpdates() {
        val enabled = _areBackgroundUpdatesEnabled.first()
        if (enabled) {
            cancelUpdateWeatherWork()
        } else {
            val repeatInterval = _backgroundUpdatesRepeatInterval.first()
            require(repeatInterval >= MIN_INTERVAL)
            scheduleBackgroundWeatherUpdateWork(repeatInterval)
        }
    }

    override suspend fun setBackgroundUpdatesRepeatInterval(repeatInterval: Long) {
        require(repeatInterval >= MIN_INTERVAL)
        dataStore.edit { preferences ->
            preferences[BACKGROUND_UPDATES_REPEAT_INTERVAL] = repeatInterval
        }
        val enabled = _areBackgroundUpdatesEnabled.first()
        if (enabled) scheduleBackgroundWeatherUpdateWork(repeatInterval)
    }

    private fun scheduleBackgroundWeatherUpdateWork(repeatInterval: Long) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest =
            PeriodicWorkRequestBuilder<UpdateWeatherWorker>(repeatInterval, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        workManager.enqueueUniquePeriodicWork(
            UPDATE_WEATHER_WORK,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    private fun cancelUpdateWeatherWork() = workManager.cancelUniqueWork(UPDATE_WEATHER_WORK)
}