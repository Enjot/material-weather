package pl.audiotocom.enjot.materialweather.core.data.di


import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.work.WorkManager
import com.enjot.materialweather.core.domain.DispatcherProvider
import com.enjot.materialweather.core.domain.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import pl.audiotocom.enjot.materialweather.core.data.location.LocationRepositoryImpl
import pl.audiotocom.enjot.materialweather.core.data.remote.HttpClientFactory
import pl.audiotocom.enjot.materialweather.core.data.util.StandardDispatchers

val coreDataModule = module {
    singleOf(::StandardDispatchers) { bind<DispatcherProvider>() }

    single { WorkManager.getInstance(androidContext()) }

    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }.bind<FusedLocationProviderClient>()

    single { HttpClientFactory.build() }

    single {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { androidContext().preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }.bind()

    singleOf(::LocationRepositoryImpl) { bind<LocationRepository>() }
}

private const val USER_PREFERENCES = "user_preferences"