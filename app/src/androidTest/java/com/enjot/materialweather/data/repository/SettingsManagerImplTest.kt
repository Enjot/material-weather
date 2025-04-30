package com.enjot.materialweather.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.enjot.materialweather.data.SettingsManagerImpl
import com.enjot.materialweather.data.SettingsManagerImpl.Companion.BACKGROUND_UPDATES_REPEAT_INTERVAL
import com.enjot.materialweather.data.SettingsManagerImpl.Companion.DEFAULT_BACKGROUND_UPDATES_REPEAT_INTERVAL
import com.enjot.materialweather.data.background.UPDATE_WEATHER_WORK
import com.enjot.materialweather.data.util.isEnqueuedOrRunning
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertFailsWith

private const val TEST_DATASTORE_NAME: String = "test_datastore"

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SettingsManagerImplTest {

    private lateinit var context: Context
    private lateinit var coroutineDispatcher: TestDispatcher
    private lateinit var coroutineScope: TestScope
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var workManager: WorkManager
    private lateinit var settingsManager: SettingsManagerImpl

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        coroutineDispatcher = StandardTestDispatcher()
        coroutineScope = TestScope(coroutineDispatcher + Job())
        dataStore = PreferenceDataStoreFactory.create(
            scope = coroutineScope,
            produceFile = { context.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
        )
        Dispatchers.setMain(coroutineDispatcher)

        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
        workManager = WorkManager.getInstance(context)
        settingsManager = SettingsManagerImpl(workManager, dataStore)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        coroutineScope.runTest { dataStore.edit { it.clear() } }
        coroutineScope.cancel()
        workManager.cancelAllWork()
    }

    @Test
    fun `Initial enabling of background updates should schedule a work request`() = runTest {
        settingsManager.toggleBackgroundUpdates()

        advanceUntilIdle()

        val workInfos = workManager.getWorkInfosForUniqueWorkFlow(UPDATE_WEATHER_WORK).first()

        assertThat(workInfos.size).isEqualTo(1)
        val settings = settingsManager.settingsStateFlow().first()
        assertThat(settings.backgroundUpdatesEnabled).isEqualTo(true)
    }

    @Test
    fun `Cancels work request when disabling background updates`() = runTest {
        settingsManager.toggleBackgroundUpdates()
        settingsManager.toggleBackgroundUpdates()

        advanceUntilIdle()

        val workInfos = workManager.getWorkInfosForUniqueWorkFlow(UPDATE_WEATHER_WORK).first()
        val activeWorkInfos = workInfos
            .filter { it.state.isEnqueuedOrRunning() }

        assertThat(activeWorkInfos.size).isEqualTo(0)
    }

    @Test
    fun `Changing repeat interval does not increase number of work requests`() = runTest {
        settingsManager.toggleBackgroundUpdates()
        settingsManager.setBackgroundUpdatesRepeatInterval(300)

        advanceUntilIdle()

        val workInfos = workManager.getWorkInfosForUniqueWorkFlow(UPDATE_WEATHER_WORK).first()

        assertThat(workInfos.size).isEqualTo(1)
    }

    @Test
    fun `Setting background updates repeat interval before enabling does not schedule work`() =
        runTest {
            settingsManager.setBackgroundUpdatesRepeatInterval(60L)

            advanceUntilIdle()

            val workInfos = workManager.getWorkInfosForUniqueWorkFlow(UPDATE_WEATHER_WORK).first()

            assertThat(workInfos).hasSize(0)
        }

    @Test
    fun `Toggle background updates re-enables after disabling`() = runTest {
        // 1st toggle: enable
        settingsManager.toggleBackgroundUpdates()
        advanceUntilIdle()
        var workInfos = workManager.getWorkInfosForUniqueWorkFlow(UPDATE_WEATHER_WORK).first()
        assertThat(workInfos.filter { it.state.isEnqueuedOrRunning() }).hasSize(1)

        // 2st toggle: disable
        settingsManager.toggleBackgroundUpdates()
        advanceUntilIdle()
        workInfos = workManager.getWorkInfosForUniqueWorkFlow(UPDATE_WEATHER_WORK).first()
        assertThat(workInfos.filter { it.state.isEnqueuedOrRunning() }).hasSize(0)

        // 3rd toggle: re-enable
        settingsManager.toggleBackgroundUpdates()
        advanceUntilIdle()
        workInfos = workManager.getWorkInfosForUniqueWorkFlow(UPDATE_WEATHER_WORK).first()
        assertThat(workInfos.filter { it.state.isEnqueuedOrRunning() }).hasSize(1)
    }

    @Test
    fun `Set background updates repeat interval throws when below min`() = runTest {
        assertFailsWith<IllegalArgumentException> {
            settingsManager.setBackgroundUpdatesRepeatInterval(10L)
        }
    }

    @Test
    fun `Toggle background updates throws when stored interval below min`() = runTest {
        dataStore.edit { it[BACKGROUND_UPDATES_REPEAT_INTERVAL] = 10L }
        advanceUntilIdle()

        assertFailsWith<IllegalArgumentException> { settingsManager.toggleBackgroundUpdates() }
    }

    @Test
    fun `Settings flow emits correct sequence of state`() = runTest {
        settingsManager.settingsStateFlow().test {
            val emitted = awaitItem()
            assertThat(emitted.backgroundUpdatesEnabled).isFalse()
            assertThat(emitted.backgroundUpdatesRepeatInterval).isEqualTo(
                DEFAULT_BACKGROUND_UPDATES_REPEAT_INTERVAL
            )

            settingsManager.toggleBackgroundUpdates()
            val afterEnable = awaitItem()
            assertThat(afterEnable.backgroundUpdatesEnabled).isTrue()
            assertThat(afterEnable.backgroundUpdatesRepeatInterval).isEqualTo(
                DEFAULT_BACKGROUND_UPDATES_REPEAT_INTERVAL
            )

            settingsManager.setBackgroundUpdatesRepeatInterval(120L)
            val afterInterval = awaitItem()
            assertThat(afterInterval.backgroundUpdatesEnabled).isTrue()
            assertThat(afterInterval.backgroundUpdatesRepeatInterval).isEqualTo(120L)

            cancelAndIgnoreRemainingEvents()
        }
    }
}