package com.compose.taptap.core.model

import kotlinx.serialization.Serializable

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

@Serializable
data class Search(
    val list: List<SearchItem>
)

@Serializable
data class SearchItem(
    val kw: String,
    val text: String,
)