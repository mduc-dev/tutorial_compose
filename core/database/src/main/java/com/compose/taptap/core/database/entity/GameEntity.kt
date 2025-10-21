package com.compose.taptap.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
@Entity
data class GameEntity(
    @PrimaryKey
    val identifier: String? = null,
    val title: String? = null,
    val iconUrl: String? = null,
    val releasedTime: Long? = null,
    val ratingScore: String? = null
)