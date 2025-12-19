package com.compose.taptap

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import com.compose.taptap.di.initKoin
import com.tencent.mmkv.MMKV

class KotlinComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        initKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@KotlinComposeApp)
        }
        SingletonImageLoader.setSafe {
            ImageLoader.Builder(this).crossfade(true).build()
        }
    }
}
