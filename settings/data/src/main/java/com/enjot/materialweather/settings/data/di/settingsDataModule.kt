package com.enjot.materialweather.settings.data.di

import com.enjot.materialweather.settings.data.SettingsManagerImpl
import com.enjot.materialweather.settings.data.UpdateWeatherWorker
import com.enjot.materialweather.settings.domain.SettingsManager
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsDataModule = module {

    workerOf(::UpdateWeatherWorker)

    singleOf(::SettingsManagerImpl) { bind<SettingsManager>() }
}