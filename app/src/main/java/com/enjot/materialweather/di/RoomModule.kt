package com.enjot.materialweather.di

import android.app.Application
import androidx.room.Room
import com.enjot.materialweather.data.database.WeatherDatabase
import com.enjot.materialweather.data.database.saved.SavedLocationDao
import com.enjot.materialweather.data.database.weather.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    
    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application): WeatherDatabase {
        return Room
            .databaseBuilder(
                app,
                WeatherDatabase::class.java,
                "weather_db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao = database.weatherDao
    
    @Provides
    fun provideSavedLocationDao(database: WeatherDatabase): SavedLocationDao = database.savedLocationDao
}
