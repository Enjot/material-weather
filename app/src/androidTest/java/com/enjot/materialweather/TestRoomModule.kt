package com.enjot.materialweather

import android.content.Context
import androidx.room.Room
import com.enjot.materialweather.data.database.SavedLocationDao
import com.enjot.materialweather.data.database.WeatherDao
import com.enjot.materialweather.data.database.WeatherDatabase
import com.enjot.materialweather.di.RoomModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RoomModule::class]
)
object TestRoomModule {
    
    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext appContext: Context): WeatherDatabase {
        return Room
            .inMemoryDatabaseBuilder(appContext, WeatherDatabase::class.java)
            .build()
    }
    
    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao = database.weatherDao()
    
    @Provides
    fun provideSavedLocationDao(database: WeatherDatabase): SavedLocationDao = database.savedLocationDao()
}
