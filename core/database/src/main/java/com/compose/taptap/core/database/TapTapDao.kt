package com.compose.taptap.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compose.taptap.core.database.entity.TapTapEntity

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

//TODO: write exactly the name of game here
@Dao
interface TapTapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTapTapList(taptapList: List<TapTapEntity>)

    @Query("SELECT * FROM TapTapEntity WHERE page = :page_")
    suspend fun getTapTapList(page_: Int): List<TapTapEntity>

    @Query("SELECT * FROM TapTapEntity WHERE page <= :page_")
    suspend fun getAllTapTapList(page_: Int): List<TapTapEntity>
}