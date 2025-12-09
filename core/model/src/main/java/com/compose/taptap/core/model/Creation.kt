package com.compose.taptap.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by duc on 09/12/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
@Serializable
data class CreationVoteData(
    @SerialName("list") val list: List<CreationVoteItem>? = null
)

@Serializable
data class CreationVoteItem(
    @SerialName("id_str") val idStr: String? = null,
    @SerialName("id") val id: Long? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("value") val value: String? = null
)
