package com.enjot.materialweather

import android.app.Application
import androidx.work.WorkManager
import com.enjot.materialweather.data.background.UpdateWeatherWorker
import com.enjot.materialweather.data.remote.openweathermap.KtorRepository
import com.enjot.materialweather.data.repository.LocalRepositoryImpl
import com.enjot.materialweather.data.repository.LocationRepositoryImpl
import com.enjot.materialweather.data.repository.WorkSchedulerImpl
import com.enjot.materialweather.di.dataStoreModule
import com.enjot.materialweather.di.databaseModule
import com.enjot.materialweather.di.dispatchersModule
import com.enjot.materialweather.di.locationModule
import com.enjot.materialweather.di.networkModule
import com.enjot.materialweather.domain.repository.LocalRepository
import com.enjot.materialweather.domain.repository.LocationRepository
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.repository.WorkScheduler
import com.enjot.materialweather.domain.usecase.savedlocation.AddSavedLocationUseCase
import com.enjot.materialweather.domain.usecase.savedlocation.DeleteSavedLocationUseCase
import com.enjot.materialweather.domain.usecase.savedlocation.SavedLocationsFlow
import com.enjot.materialweather.domain.usecase.search.GetSearchResultsUseCase
import com.enjot.materialweather.domain.usecase.settings.ManageWeatherUpdateUseCase
import com.enjot.materialweather.domain.usecase.weather.GetWeatherFromLocationUseCase
import com.enjot.materialweather.domain.usecase.weather.LocalWeatherFlow
import com.enjot.materialweather.domain.usecase.weather.UpdateWeatherUseCase
import com.enjot.materialweather.presentation.ui.screen.daily.DailyViewModel
import com.enjot.materialweather.presentation.ui.screen.overview.OverviewViewModel
import com.enjot.materialweather.presentation.ui.screen.settings.SettingsViewModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.KoinComponent
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module
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
            dispatchersModule,
            databaseModule,
            dataStoreModule,
            networkModule,
            locationModule,
            weatherModule
        )
        workManagerFactory()
    }
}

val weatherModule = module {
    single { WorkManager.getInstance(androidContext()) }

    singleOf(::WorkSchedulerImpl) { bind<WorkScheduler>() }
    singleOf(::LocalRepositoryImpl) { bind<LocalRepository>() }
    singleOf(::KtorRepository) { bind<RemoteRepository>() }
    singleOf(::LocationRepositoryImpl) { bind<LocationRepository>() }

    singleOf(::LocalWeatherFlow)
    singleOf(::SavedLocationsFlow)
    singleOf(::ManageWeatherUpdateUseCase)
    singleOf(::GetSearchResultsUseCase)
    singleOf(::UpdateWeatherUseCase)
    singleOf(::AddSavedLocationUseCase)
    singleOf(::DeleteSavedLocationUseCase)
    singleOf(::GetWeatherFromLocationUseCase)

    workerOf(::UpdateWeatherWorker)

    viewModelOf(::OverviewViewModel)
    viewModelOf(::DailyViewModel)
    viewModelOf(::SettingsViewModel)
}