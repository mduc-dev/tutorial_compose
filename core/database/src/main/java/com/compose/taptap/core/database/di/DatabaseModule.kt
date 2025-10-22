package com.compose.taptap.core.database.di

import androidx.room.Room
import com.compose.taptap.core.database.TapTapDatabase
import com.compose.taptap.core.database.converter.StringListConverter
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

fun databaseModule() = module {
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
            coerceInputValues = true
        }
    }

    single { StringListConverter(get()) }

    single {
        Room.databaseBuilder(
            androidApplication(),
            TapTapDatabase::class.java,
            "TapTap.db"
        )
            .fallbackToDestructiveMigration(false)
            .addTypeConverter(get<StringListConverter>())
            .build()
    }

    single { get<TapTapDatabase>().gameDao() }
    single { get<TapTapDatabase>().gameInfoDao() }
}