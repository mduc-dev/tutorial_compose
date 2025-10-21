package com.compose.taptap.core.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compose.taptap.core.database.entity.GameInfoEntity

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

//TODO: write exactly the name of game here
@Dao
interface GameInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameInfo(taptapInfo: GameInfoEntity)

    @Query("SELECT * FROM GameInfoEntity WHERE id = :id_")
    suspend fun getGameInfo(id_: String): GameInfoEntity?
}