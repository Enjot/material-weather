package com.enjot.materialweather.app

import android.app.Application
import com.enjot.materialweather.BuildConfig
import com.enjot.materialweather.settings.data.di.settingsDataModule
import com.enjot.materialweather.settings.presentation.di.settingsPresentationModule
import com.enjot.materialweather.weather.data.di.weatherDataModule
import com.enjot.materialweather.weather.presentation.di.weatherPresentationModule
import com.enjot.materialweather.widget.widgetModule
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.KoinComponent
import org.koin.dsl.koinConfiguration
import pl.audiotocom.enjot.materialweather.core.data.di.coreDataModule
import timber.log.Timber

@OptIn(KoinExperimentalAPI::class)
class MaterialWeatherApplication : Application(), KoinStartup, KoinComponent {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(
                object : Timber.DebugTree() {
                    override fun createStackElementTag(element: StackTraceElement): String {
                        return with(element) { "$fileName:$lineNumber $methodName()" }
                    }
                }
            )

            val crashlytics = FirebaseCrashlytics.getInstance()
            crashlytics.isCrashlyticsCollectionEnabled = false
        }
    }

    override fun onKoinStartup() = koinConfiguration {
        androidLogger()
        androidContext(this@MaterialWeatherApplication)
        modules(
            coreDataModule,
            settingsDataModule,
            settingsPresentationModule,
            weatherDataModule,
            weatherPresentationModule,
            widgetModule
        )
        workManagerFactory()
    }
}