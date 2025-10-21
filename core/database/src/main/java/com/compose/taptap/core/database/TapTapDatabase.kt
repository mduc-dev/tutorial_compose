package com.compose.taptap.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.compose.taptap.core.database.converter.StringListConverter
import com.compose.taptap.core.database.entity.GameEntity
import com.compose.taptap.core.database.entity.GameInfoEntity

@Database(
    entities = [GameEntity::class, GameInfoEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(value = [StringListConverter::class])
abstract class TapTapDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun gameInfoDao(): GameInfoDao
}