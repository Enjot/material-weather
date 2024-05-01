package com.enjot.materialweather.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.enjot.materialweather.domain.repository.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DATASTORE_NAME: String = "test_datastore"

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PreferencesRepositoryImplTest {

    private val testContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher + Job())

    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile =
            { testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
        )
    
    private val repository: PreferencesRepository = PreferencesRepositoryImpl(testDataStore)

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
        testCoroutineScope.runBlockingTest {
            testDataStore.edit { it.clear() }
        }
        testCoroutineScope.cancel()
    }

    @Test
    fun `Initial flow emits right value`() = runTest {

        repository.getUserPreferencesFlow().test {
            val initialInterval = awaitItem().backgroundUpdatesRepeatInterval

            assertThat(initialInterval).isEqualTo(30L)
        }
    }

    @Test
    fun `Flow emits new value after getting updated by repository`() = runTest {

        repository.getUserPreferencesFlow().test {

            val initialInterval = awaitItem().backgroundUpdatesRepeatInterval
            assertThat(initialInterval).isEqualTo(30L)

            repository.setBackgroundUpdatesRepeatInterval(120L)

            val newInterval = awaitItem().backgroundUpdatesRepeatInterval

            assertThat(newInterval).isEqualTo(120L)
        }
    }
}
