package com.enjot.materialweather.di

import android.content.Context
import androidx.room.Room
import com.enjot.materialweather.data.database.SavedLocationDao
import com.enjot.materialweather.data.database.WeatherDao
import com.enjot.materialweather.data.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    
    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext appContext: Context): WeatherDatabase {
        return Room
            .databaseBuilder(
                appContext,
                WeatherDatabase::class.java,
                "weather_db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao = database.weatherDao()
    
    @Provides
    fun provideSavedLocationDao(database: WeatherDatabase): SavedLocationDao = database.savedLocationDao()
}
