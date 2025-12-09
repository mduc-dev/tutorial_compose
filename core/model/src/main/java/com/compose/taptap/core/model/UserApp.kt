package com.compose.taptap.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Created by duc on 09/12/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
typealias UserAppData = PaginationData<UserAppItem>

@Serializable
data class UserAppItem(
    @SerialName("identifier") val identifier: String? = null,
    @SerialName("is_bought") val isBought: Boolean? = null,
    @SerialName("spent") val spent: Int? = null,
    @SerialName("played_tips") val playedTips: String? = null,
    @SerialName("updated_time") val updatedTime: Long? = null,
    @SerialName("achievement_completed") val achievementCompleted: Int? = null,
    @SerialName("achievement_total") val achievementTotal: Int? = null
)

typealias UserAppStatusData = PaginationData<UserAppStatusItem>

@Serializable
data class UserAppStatusItem(
    @SerialName("app") val app: AppDetail? = null,
    @SerialName("post") val post: JsonElement? = null,
    @SerialName("app_id") val appId: Long? = null,
    @SerialName("status") val status: Int? = null,
    @SerialName("score") val score: Int? = null,
    @SerialName("created_time") val createdTime: Long? = null,
    @SerialName("reserve") val reserve: Boolean? = null
)
