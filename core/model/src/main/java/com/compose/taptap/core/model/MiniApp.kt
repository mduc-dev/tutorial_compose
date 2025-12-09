package com.compose.taptap.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Created by duc on 09/12/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
typealias MiniMultiGetData = PaginationData<AppDetail>

@Serializable
data class AppDetail(
    @SerialName("id") val id: Long? = null,
    @SerialName("identifier") val identifier: String? = null,
    @SerialName("itunes_id") val itunesId: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("title_labels") val titleLabels: List<String>? = null,
    @SerialName("icon") val icon: Icon? = null, // Replaced AppIcon with Icon
    @SerialName("price") val price: AppPrice? = null,
    @SerialName("uri") val uri: AppUri? = null,
    @SerialName("can_view") val canView: Boolean? = null,
    @SerialName("released_time") val releasedTime: Long? = null,
    @SerialName("button_flag") val buttonFlag: Int? = null,
    @SerialName("button_label") val buttonLabel: String? = null,
    @SerialName("button_params") val buttonParams: JsonElement? = null,
    @SerialName("style") val style: Int? = null,
    @SerialName("hidden_button") val hiddenButton: Boolean? = null,
    @SerialName("is_deny_minors") val isDenyMinors: Boolean? = null,
    @SerialName("stat") val stat: AppStat? = null,
    @SerialName("ad_banner") val adBanner: AppBanner? = null,
    @SerialName("top_banner") val topBanner: AppBanner? = null,
    @SerialName("banner") val banner: AppBanner? = null,
    @SerialName("whatsnew") val whatsNew: AppWhatsNew? = null,
    @SerialName("tags") val tags: List<AppTag>? = null,
    @SerialName("log") val log: JsonElement? = null,
    @SerialName("event_log") val eventLog: JsonElement? = null,
    @SerialName("developers") val developers: List<AppDeveloper>? = null,
    @SerialName("can_buy_redeem_code") val canBuyRedeemCode: JsonElement? = null,
    @SerialName("show_module") val showModule: List<AppModule>? = null,
    @SerialName("complaint") val complaint: AppComplaint? = null,
    @SerialName("package_info") val packageInfo: AppPackageInfo? = null,
    @SerialName("serial_number") val serialNumber: JsonElement? = null,
    @SerialName("readable_id") val readableId: String? = null,
    @SerialName("supported_platforms") val supportedPlatforms: List<AppPlatform>? = null,
    @SerialName("m_button_map") val mButtonMap: JsonElement? = null,
    @SerialName("description") val description: AppDescription? = null,
    @SerialName("stat_key") val statKey: String? = null,
    @SerialName("include_app_product_type_complete") val includeAppProductTypeComplete: Boolean? = null,
    @SerialName("is_console_game") val isConsoleGame: Boolean? = null,
    @SerialName("is_level") val isLevel: Boolean? = null,
    @SerialName("level_rating_enabled") val levelRatingEnabled: Boolean? = null,
    @SerialName("editor_choice") val editorChoice: Boolean? = null,
    @SerialName("rec_text") val recText: String? = null
)

@Serializable
data class AppPrice(
    @SerialName("taptap_current") val taptapCurrent: String? = null,
    @SerialName("discount_rate") val discountRate: Int? = null
)

@Serializable
data class AppUri(
    @SerialName("google") val google: String? = null,
    @SerialName("google_play") val googlePlay: String? = null,
    @SerialName("apple") val apple: String? = null,
    @SerialName("download_site") val downloadSite: String? = null
)

@Serializable
data class AppStat(
    @SerialName("rating") val rating: AppRating? = null,
    @SerialName("vote_info") val voteInfo: Map<String, Int>? = null,
    @SerialName("hits_total") val hitsTotal: Long? = null,
    @SerialName("play_total") val playTotal: Long? = null,
    @SerialName("bought_count") val boughtCount: Long? = null,
    @SerialName("feed_count") val feedCount: Long? = null,
    @SerialName("reserve_count") val reserveCount: Long? = null,
    @SerialName("recent_sandbox_played_count") val recentSandboxPlayedCount: Long? = null,
    @SerialName("album_count") val albumCount: Long? = null,
    @SerialName("review_count") val reviewCount: Long? = null,
    @SerialName("topic_count") val topicCount: Long? = null,
    @SerialName("video_count") val videoCount: Long? = null,
    @SerialName("official_topic_count") val officialTopicCount: Long? = null,
    @SerialName("official_video_count") val officialVideoCount: Long? = null,
    @SerialName("official_album_count") val officialAlbumCount: Long? = null,
    @SerialName("fans_count") val fansCount: Long? = null,
    @SerialName("hits_total_val") val hitsTotalVal: Long? = null,
    @SerialName("pc_sale_count") val pcSaleCount: Long? = null,
    @SerialName("pc_download_count") val pcDownloadCount: Long? = null,
    @SerialName("wish_count") val wishCount: Long? = null,
    @SerialName("level_like_count") val levelLikeCount: Long? = null
)

@Serializable
data class AppRating(
    @SerialName("score") val score: String? = null,
    @SerialName("max") val max: Int? = null,
    @SerialName("latest_score") val latestScore: String? = null,
    @SerialName("latest_version_score") val latestVersionScore: String? = null,
    @SerialName("latest_review_count") val latestReviewCount: Long? = null,
    @SerialName("latest_version_review_count") val latestVersionReviewCount: Long? = null
)

@Serializable
data class AppBanner(
    @SerialName("url") val url: String? = null,
    @SerialName("medium_url") val mediumUrl: String? = null,
    @SerialName("small_url") val smallUrl: String? = null,
    @SerialName("original_url") val originalUrl: String? = null,
    @SerialName("original_format") val originalFormat: String? = null,
    @SerialName("width") val width: Int? = null,
    @SerialName("height") val height: Int? = null,
    @SerialName("color") val color: String? = null,
    @SerialName("original_size") val originalSize: Long? = null
)

@Serializable
data class AppWhatsNew(
    @SerialName("text") val text: String? = null
)

@Serializable
data class AppTag(
    @SerialName("id") val id: Long? = null,
    @SerialName("value") val value: String? = null,
    @SerialName("uri") val uri: String? = null,
    @SerialName("web_url") val webUrl: String? = null
)

@Serializable
data class AppDeveloper(
    @SerialName("id") val id: Long? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("label") val label: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("uri") val uri: String? = null,
    @SerialName("is_online") val isOnline: Boolean? = null,
    @SerialName("avatar") val avatar: Icon? = null // Replaced AppIcon with Icon
)

@Serializable
data class AppModule(
    @SerialName("key") val key: String? = null,
    @SerialName("value") val value: Boolean? = null
)

@Serializable
data class AppComplaint(
    @SerialName("uri") val uri: String? = null,
    @SerialName("web_url") val webUrl: String? = null,
    @SerialName("url") val url: String? = null
)

@Serializable
data class AppPackageInfo(
    @SerialName("package_name") val packageName: String? = null,
    @SerialName("package_label") val packageLabel: String? = null,
    @SerialName("is_channel") val isChannel: Boolean? = null
)

@Serializable
data class AppPlatform(
    @SerialName("key") val key: String? = null,
    @SerialName("released_time") val releasedTime: Long? = null
)

@Serializable
data class AppDescription(
    @SerialName("text") val text: String? = null
)
