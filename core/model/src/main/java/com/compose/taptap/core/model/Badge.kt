package com.compose.taptap.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Created by duc on 09/12/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
@Serializable
data class EarnBadgeData(
    @SerialName("info") val info: JsonElement? = null
)

@Serializable
data class BadgeWearInfoData(
    @SerialName("total") val total: Int? = null,
    @SerialName("info") val info: JsonElement? = null
)

typealias BadgeListData = PaginationData<BadgeItem>

@Serializable
data class BadgeItem(
    @SerialName("id") val id: Long? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("earn_link") val earnLink: String? = null,
    @SerialName("congratulate_description") val congratulateDescription: String? = null,
    @SerialName("congratulate_button_text") val congratulateButtonText: String? = null,
    @SerialName("congratulate_link") val congratulateLink: String? = null,
    @SerialName("small_image") val smallImage: Icon? = null, // Replaced AppIcon with Icon
    @SerialName("middle_image") val middleImage: Icon? = null, // Replaced AppIcon with Icon
    @SerialName("large_image") val largeImage: Icon? = null, // Replaced AppIcon with Icon
    @SerialName("is_earned") val isEarned: Boolean? = null,
    @SerialName("is_wear") val isWear: Boolean? = null,
    @SerialName("can_earn") val canEarn: Boolean? = null,
    @SerialName("earned_count") val earnedCount: Int? = null
)
