package com.example.kotlin_compose

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade
import com.example.kotlin_compose.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class KotlinComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@KotlinComposeApp)
        }
        SingletonImageLoader.setSafe {
            ImageLoader.Builder(this)
                .crossfade(true)
                .build()
        }
    }
}