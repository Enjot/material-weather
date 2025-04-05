package com.enjot.materialweather.di

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module


val locationModule = module {
    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }.bind<FusedLocationProviderClient>()
}