package com.compose.taptap.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Created by duc on 09/12/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
typealias FeedByMeData = PaginationData<FeedItem>

@Serializable
data class FeedItem(
    @SerialName("type") val type: String? = null,
    @SerialName("identification") val identification: String? = null,
    @SerialName("post") val post: PostDetail? = null
)

@Serializable
data class PostDetail(
    @SerialName("id_str") val idStr: String? = null,
    @SerialName("id") val id: Long? = null,
    @SerialName("user") val user: FeedUser? = null,
    @SerialName("type") val type: Int? = null,
    @SerialName("pin_video") val pinVideo: JsonElement? = null,
    @SerialName("list_fields") val listFields: ListFields? = null,
    @SerialName("actions") val actions: FeedActions? = null,
    @SerialName("stat") val stat: FeedStat? = null,
    @SerialName("published_time") val publishedTime: Long? = null,
    @SerialName("is_scheduling") val isScheduling: Boolean? = null,
    @SerialName("edited_time") val editedTime: Long? = null,
    @SerialName("visibility") val visibility: Int? = null,
    @SerialName("sharing") val sharing: FeedSharing? = null,
    @SerialName("event_log") val eventLog: JsonElement? = null,
    @SerialName("complaint") val complaint: AppComplaint? = null,
    @SerialName("gallery_pictures") val galleryPictures: List<GalleryPicture>? = null
)

@Serializable
data class FeedUser(
    @SerialName("id") val id: Long? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("avatar") val avatar: String? = null,
    @SerialName("medium_avatar") val mediumAvatar: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("store") val store: String? = null,
    @SerialName("intro") val intro: String? = null,
    @SerialName("is_certified") val isCertified: Boolean? = null,
    @SerialName("is_anonymous") val isAnonymous: Boolean? = null,
    @SerialName("is_ban") val isBan: Boolean? = null,
    @SerialName("is_deactivated") val isDeactivated: Boolean? = null,
    @SerialName("stat") val stat: UserStat? = null,
    @SerialName("log") val log: JsonElement? = null,
    @SerialName("event_log") val eventLog: JsonElement? = null,
    @SerialName("is_silent") val isSilent: Boolean? = null
)

@Serializable
data class UserStat(
    @SerialName("fans_count") val fansCount: Int? = null,
    @SerialName("following_count") val followingCount: Int? = null
)

@Serializable
data class ListFields(
    @SerialName("title") val title: String? = null,
    @SerialName("summary") val summary: String? = null,
    @SerialName("cover") val cover: Icon? = null // Replaced AppIcon with Icon
)

@Serializable
data class FeedActions(
    @SerialName("update") val update: Boolean? = null,
    @SerialName("delete") val delete: Boolean? = null,
    @SerialName("comment") val comment: Boolean? = null
)

@Serializable
data class FeedStat(
    @SerialName("pv_total") val pvTotal: Int? = null,
    @SerialName("ups") val ups: Int? = null,
    @SerialName("comments") val comments: Int? = null
)

@Serializable
data class FeedSharing(
    @SerialName("url") val url: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("image") val image: Icon? = null, // Replaced AppIcon with Icon
    @SerialName("message_params") val messageParams: JsonElement? = null
)

@Serializable
data class GalleryPicture(
    @SerialName("image") val image: Icon? = null // Replaced AppIcon with Icon
)
