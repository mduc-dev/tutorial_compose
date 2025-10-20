package com.compose.taptap.data.di

import com.compose.taptap.core.network.di.networkModule
import com.compose.taptap.core.network.di.dispatchersModule
import com.compose.taptap.core.database.di.databaseModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule(), networkModule(), databaseModule(), dispatchersModule())
}
