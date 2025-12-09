package com.compose.taptap.core.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Created by duc on 09/12/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

@Immutable
@Serializable
data class UserProfileData(
    @SerialName("id") val id: Long? = null,
    @SerialName("store") val store: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("nickname") val nickname: String? = null,
    @SerialName("name_is_under_review") val nameIsUnderReview: Boolean? = null,
    @SerialName("avatar") val avatar: String? = null,
    @SerialName("medium_avatar") val mediumAvatar: String? = null,
    @SerialName("avatar_is_under_review") val avatarIsUnderReview: Boolean? = null,
    @SerialName("avatar_pendant") val avatarPendant: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("intro") val intro: String? = null,
    @SerialName("intro_is_under_review") val introIsUnderReview: Boolean? = null,
    @SerialName("background_image_is_under_review") val backgroundImageIsUnderReview: Boolean? = null,
    @SerialName("is_teen") val isTeen: Boolean? = null,
    @SerialName("is_silent") val isSilent: Boolean? = null,
    @SerialName("is_deactivated") val isDeactivated: Boolean? = null,
    @SerialName("is_deleted") val isDeleted: Boolean? = null,
    @SerialName("is_certified") val isCertified: Boolean? = null,
    @SerialName("email") val email: UserEmail? = null,
    @SerialName("ip_location") val ipLocation: String? = null,
    @SerialName("socials") val socials: List<UserSocial>? = null,
    @SerialName("wechat_push") val wechatPush: JsonElement? = null,
    @SerialName("sharing") val sharing: FeedSharing? = null,
    @SerialName("log") val log: JsonElement? = null,
    @SerialName("event_log") val eventLog: JsonElement? = null,
    @SerialName("complaint") val complaint: AppComplaint? = null,
    @SerialName("menu") val menu: UserMenu? = null,
    @SerialName("stats") val stats: MetricData? = null,
    @SerialName("show_setting") val showSetting: UserShowSetting? = null,
    @SerialName("fake_age") val fakeAge: Int? = null
)

@Serializable
data class UserEmail(
    @SerialName("address") val address: String? = null,
    @SerialName("verified") val verified: Boolean? = null
)

@Serializable
data class UserSocial(
    @SerialName("provider") val provider: String? = null,
    @SerialName("name") val name: String? = null
)

@Serializable
data class UserMenu(
    @SerialName("admin_page") val adminPage: Boolean? = null,
    @SerialName("developer_center") val developerCenter: Boolean? = null,
    @SerialName("payment_settings") val paymentSettings: Boolean? = null
)

@Serializable
data class UserShowSetting(
    @SerialName("show_forum_level") val showForumLevel: Boolean? = null,
    @SerialName("show_app_wishlist") val showAppWishlist: Boolean? = null,
    @SerialName("game_record_large_card") val gameRecordLargeCard: Boolean? = null,
    @SerialName("show_steam_record") val showSteamRecord: Boolean? = null,
    @SerialName("show_steam_record_in_user_app") val showSteamRecordInUserApp: Boolean? = null
)
