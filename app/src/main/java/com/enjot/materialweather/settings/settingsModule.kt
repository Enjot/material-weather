package com.enjot.materialweather.settings


import com.enjot.materialweather.settings.data.SettingsManagerImpl
import com.enjot.materialweather.settings.data.UpdateWeatherWorker
import com.enjot.materialweather.settings.domain.SettingsManager
import com.enjot.materialweather.weather.presentation.ui.screen.settings.SettingsViewModel
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {

    workerOf(::UpdateWeatherWorker)

    singleOf(::SettingsManagerImpl) { bind<SettingsManager>() }

    viewModelOf(::SettingsViewModel)
}