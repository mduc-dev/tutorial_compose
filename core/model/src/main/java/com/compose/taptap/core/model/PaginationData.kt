package com.compose.taptap.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by duc on 09/12/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
@Serializable
data class PaginationData<T>(
    @SerialName("list") val list: List<T>? = null,
    @SerialName("prev_page") val prevPage: String? = null,
    @SerialName("next_page") val nextPage: String? = null,
    @SerialName("total") val total: Int? = null
)
