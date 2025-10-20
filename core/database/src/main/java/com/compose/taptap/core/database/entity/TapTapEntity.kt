package com.compose.taptap.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

@Entity
data class TapTapEntity(
    val page: Int = 0,

    @PrimaryKey val id: String
)