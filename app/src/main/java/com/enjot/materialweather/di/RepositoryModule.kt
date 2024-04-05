package com.enjot.materialweather.di

import com.enjot.materialweather.data.repository.LocalRepositoryImpl
import com.enjot.materialweather.data.repository.LocationRepositoryImpl
import com.enjot.materialweather.data.repository.PreferencesRepositoryImpl
import com.enjot.materialweather.data.repository.RemoteRepositoryImpl
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
        remoteRepositoryImpl: RemoteRepositoryImpl
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
    abstract fun providesLocationTracker(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
    
    @Binds
    @Singleton
    abstract fun providesPreferencesRepository(
        preferencesDataStore: PreferencesRepositoryImpl
    ): PreferencesRepository
}
