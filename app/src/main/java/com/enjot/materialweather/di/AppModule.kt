package com.enjot.materialweather.di

import android.app.Application
import androidx.room.Room
import com.enjot.materialweather.data.database.WeatherDatabase
import com.enjot.materialweather.data.database.saved.SavedLocationDao
import com.enjot.materialweather.data.database.weather.WeatherDao
import com.enjot.materialweather.data.remote.openweathermap.api.GeoapifyApi
import com.enjot.materialweather.data.remote.openweathermap.api.OpenWeatherMapApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
    
    @Provides
    @Named("openweathermap")
    @Singleton
    fun provideWeatherApi(client: OkHttpClient): OpenWeatherMapApi {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(OpenWeatherMapApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
            .create()
    }
    
    @Provides
    @Named("geoapify")
    @Singleton
    fun provideGeoapifyApi(client: OkHttpClient): GeoapifyApi {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(GeoapifyApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
            .create()
    }
    
    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }
    
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