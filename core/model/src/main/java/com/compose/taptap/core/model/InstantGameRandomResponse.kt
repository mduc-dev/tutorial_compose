package com.compose.taptap.core.model

import kotlinx.serialization.Serializable

@Serializable
data class InstantGameRandomResponse(
    val data: InstantGameRandomData,
    val success: Boolean
)

@Serializable
data class InstantGameRandomData(
    val info: InstantGameItem
)
