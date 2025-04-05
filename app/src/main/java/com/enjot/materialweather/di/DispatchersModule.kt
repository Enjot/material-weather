package com.enjot.materialweather.di

import com.enjot.materialweather.data.util.StandardDispatchers
import com.enjot.materialweather.domain.repository.DispatcherProvider
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val dispatchersModule = module {
    singleOf(::StandardDispatchers) { bind<DispatcherProvider>() }
}