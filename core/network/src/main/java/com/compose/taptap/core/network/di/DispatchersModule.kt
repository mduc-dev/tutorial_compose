package com.compose.taptap.core.network.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
enum class TapTapAppDispatcher {
    IO
}

fun dispatchersModule() = module {
    single<CoroutineDispatcher>(named(TapTapAppDispatcher.IO)) { Dispatchers.IO }
}