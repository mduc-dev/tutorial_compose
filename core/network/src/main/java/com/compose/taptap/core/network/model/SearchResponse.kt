package com.compose.taptap.core.network.model

import com.compose.taptap.core.model.Search
import kotlinx.serialization.Serializable

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
@Serializable
data class SearchResponse(
    val data: Search, val now: Long, val success: Boolean
) {
    fun firstTextOrDefault(default: String = "Discover Superb Games"): String {
        return data.list.firstOrNull()?.text ?: default
    }
}