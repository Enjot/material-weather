package com.enjot.materialweather.di

import com.enjot.materialweather.data.remote.openweathermap.KtorRepository
import com.enjot.materialweather.data.repository.LocalRepositoryImpl
import com.enjot.materialweather.data.repository.LocationRepositoryImpl
import com.enjot.materialweather.data.repository.PreferencesRepositoryImpl
import com.enjot.materialweather.data.repository.WorkSchedulerImpl
import com.enjot.materialweather.domain.repository.LocalRepository
import com.enjot.materialweather.domain.repository.LocationRepository
import com.enjot.materialweather.domain.repository.PreferencesRepository
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.repository.WorkScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRemoteRepository(
        remoteRepositoryImpl: KtorRepository
    ): RemoteRepository
    
    @Binds
    @Singleton
    abstract fun bindLocalRepository(
        localRepositoryImpl: LocalRepositoryImpl
    ): LocalRepository
    
    @Binds
    @Singleton
    abstract fun bindWorkScheduler(
        workSchedulerImpl: WorkSchedulerImpl
    ): WorkScheduler
    
    @Binds
    @Singleton
    abstract fun bindLocationTracker(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
    
    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        preferencesDataStore: PreferencesRepositoryImpl
    ): PreferencesRepository
}
