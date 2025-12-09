package com.compose.taptap.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("data") val data: T? = null,
    @SerialName("now") val now: Long? = null,
    @SerialName("success") val success: Boolean? = null
)
