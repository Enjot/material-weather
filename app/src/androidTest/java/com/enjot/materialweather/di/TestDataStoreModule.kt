package com.enjot.materialweather.di

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.test.TestScope
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val TEST_DATA_STORE_NAME = "test_datastore"

val testDataStoreModule = module {
    single {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = TestScope(),
            produceFile = { androidContext().preferencesDataStoreFile(TEST_DATA_STORE_NAME) }
        )
    }
}