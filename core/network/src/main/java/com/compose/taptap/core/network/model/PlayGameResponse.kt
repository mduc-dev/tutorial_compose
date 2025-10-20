package com.compose.taptap.core.network.model

import com.compose.taptap.core.model.InstantGame
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */


@Serializable
data class PlayGameResponse(
    @SerialName("data") val data: InstantGame,
    @SerialName("now") val now: Long,
    @SerialName("success") val success: Boolean,
)
