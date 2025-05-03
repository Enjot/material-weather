package com.enjot.materialweather.weather.presentation.di

import com.enjot.materialweather.weather.presentation.ui.screen.daily.DailyViewModel
import com.enjot.materialweather.weather.presentation.ui.screen.overview.OverviewViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherPresentationModule = module {
    viewModelOf(::OverviewViewModel)
    viewModelOf(::DailyViewModel)
}