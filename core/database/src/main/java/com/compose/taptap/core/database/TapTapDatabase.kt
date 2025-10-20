package com.compose.taptap.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.compose.taptap.core.database.converter.StringListConverter
import com.compose.taptap.core.database.entity.TapTapEntity
import com.compose.taptap.core.database.entity.TapTapInfoEntity

@Database(
    entities = [TapTapEntity::class, TapTapInfoEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(value = [StringListConverter::class])
abstract class TapTapDatabase : RoomDatabase() {
    abstract fun taptapDao(): TapTapDao
    abstract fun taptapInfoDao(): TapTapInfoDao
}