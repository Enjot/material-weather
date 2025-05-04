package com.enjot.materialweather.widget

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val widgetModule = module {
    singleOf(::WidgetManagerImpl).bind<WidgetManager>()
}