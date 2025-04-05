package com.enjot.materialweather

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.enjot.materialweather.di.testDataStoreModule
import com.enjot.materialweather.di.testDatabaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.KoinComponent
import org.koin.dsl.koinConfiguration

class KoinTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApplication::class.java.getName(), context)
    }
}

@OptIn(KoinExperimentalAPI::class)
class TestApplication : Application(), KoinStartup, KoinComponent {

    override fun onKoinStartup() = koinConfiguration {
        androidContext(this@TestApplication)
        modules(
            testDataStoreModule,
            testDatabaseModule
        )
        workManagerFactory()
    }
}