package com.compose.taptap.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Games(
    @SerialName("data") val data: Data,
    @SerialName("now") val now: Long,
    @SerialName("success") val success: Boolean,
)

@Serializable
data class Data(
    val list: List<ListItem>,
    @SerialName("prev_page") val prevPage: String,
    @SerialName("next_page") val nextPage: String,
)

@Serializable
data class ListItem(
    val type: String? = null,
    val identification: String? = null,
    val app: App? = null,
    @SerialName("rec_reason") val recReason: RecReason? = null,
    val category: Category? = null,
    val dailies: Dailies? = null
)

@Serializable
data class Dailies(
    @SerialName("date_key") val dateKey: String,
    val list: List<DailiesItem>,
    @SerialName("event_log") val eventLog: EventLog? = null
)

@Serializable
data class DailiesItem(
    val identification: String,
    val type: String,
    val app: App? = null,
    val sort: Int? = null,
    val description: String? = null,
    val cover: Cover? = null,
    val title: String? = null,
    @SerialName("event_end_time") val eventEndTime: Long? = null,
    val video: String? = null,
    @SerialName("video_id") val videoId: Long? = null
)

@Serializable
data class App(
    val id: Long,
    val identifier: String,
    val title: String,
    @SerialName("title_labels") val titleLabels: List<String>,
    val icon: Icon?,
    val uri: String?,
    @SerialName("can_view") val canView: Boolean,
    @SerialName("released_time") val releasedTime: Long? = null,
    val stat: Stat?,
    val banner: Banner?,
    val tags: List<Tag>?,
    val log: Log?,
    @SerialName("event_log") val eventLog: EventLog?,
    val complaint: Complaint?,
    @SerialName("supported_platforms") val supportedPlatforms: List<SupportedPlatform>? = null,
    @SerialName("itunes_id") val itunesId: String? = null,
    @SerialName("rec_text") val recText: String? = null,
    @SerialName("video_resource") val videoResource: VideoResource? = null,
)

@Serializable
data class Icon(
    val url: String,
    @SerialName("medium_url") val mediumUrl: String,
    @SerialName("small_url") val smallUrl: String,
    @SerialName("original_url") val originalUrl: String,
    @SerialName("original_format") val originalFormat: String,
    val width: Long,
    val height: Long,
    val color: String,
    @SerialName("original_size") val originalSize: Long = 0,
)

@Serializable
data class Stat(
    val rating: Rating? = null,
    @SerialName("vote_info") val voteInfo: VoteInfo? = null,
    @SerialName("hits_total") val hitsTotal: Long = 0,
    @SerialName("review_count") val reviewCount: Long = 0,
    @SerialName("fans_count") val fansCount: Long,
    @SerialName("user_want_count") val userWantCount: Long = 0,
    @SerialName("user_played_count") val userPlayedCount: Long = 0,
    @SerialName("user_playing_count") val userPlayingCount: Long = 0,
    @SerialName("reserve_count") val reserveCount: Long = 0,
)

@Serializable
data class Rating(
    val score: String,
    val max: Long,
    @SerialName("latest_score") val latestScore: String? = "",
    @SerialName("latest_version_score") val latestVersionScore: String? = "",
)

@Serializable
data class VoteInfo(
    @SerialName("1") val n1: Long,
    @SerialName("2") val n2: Long,
    @SerialName("3") val n3: Long,
    @SerialName("4") val n4: Long,
    @SerialName("5") val n5: Long,
)

@Serializable
data class Banner(
    val url: String,
    @SerialName("medium_url") val mediumUrl: String?,
    @SerialName("small_url") val smallUrl: String?,
    @SerialName("original_url") val originalUrl: String?,
    @SerialName("original_format") val originalFormat: String?,
    val width: Long?,
    val height: Long?,
    val color: String?,
    @SerialName("original_size") val originalSize: Long? = 0,
)

@Serializable
data class Tag(
    val id: Long,
    val value: String,
    val uri: String,
    @SerialName("web_url") val webUrl: String,
)

@Serializable
data class Log(
    val follow: Follow,
    val open: Open? = null,
    @SerialName("page_view") val pageView: PageView,
    val play: Play? = null,
    val reserve: Reserve? = null,
    val unfollow: Unfollow? = null,
    val unreserved: Unreserved? = null,
)

@Serializable
data class Params(
    val type: String,
    @SerialName("APIVersion") val apiversion: String,
    val paramId: Long = 0,
    val paramType: String = "",
)

@Serializable
data class Open(
    val uri: String,
    val params: Params,
)

@Serializable
data class Follow(
    val uri: String,
    val params: Params,
)

@Serializable
data class PageView(
    val uri: String,
    val params: Params,
)

@Serializable
data class Play(
    val uri: String,
    val params: Params,
)

@Serializable
data class Reserve(
    val uri: String,
    val params: Params,
)

@Serializable
data class Unfollow(
    val uri: String,
    val params: Params,
)

@Serializable
data class Unreserved(
    val uri: String,
    val params: Params,
)

@Serializable
data class EventLog(
    val paramId: String? = null,
    val paramType: String? = null,
    val via: String? = null,
)

@Serializable
data class Complaint(
    val uri: String,
    @SerialName("web_url") val webUrl: String,
    val url: String,
)

@Serializable
data class SupportedPlatform(
    val key: String = "",
    @SerialName("released_time") val releasedTime: Long = 0,
)

@Serializable
data class VideoResource(
    @SerialName("video_id") val videoId: Long,
    @SerialName("play_url") val playUrl: PlayUrl,
    val info: Info,
    val thumbnail: Thumbnail,
    @SerialName("raw_cover") val rawCover: RawCover? = null,
    @SerialName("play_log") val playLog: PlayLog,
    val status: Status,
)

@Serializable
data class PlayUrl(
    val url: String,
    @SerialName("url_h265") val urlH265: String,
    @SerialName("url_expires") val urlExpires: Long,
)

@Serializable
data class Info(
    @SerialName("aspect_ratio") val aspectRatio: Double,
    @SerialName("best_format_name") val bestFormatName: String,
    val duration: Long,
)

@Serializable
data class Thumbnail(
    val url: String,
    @SerialName("medium_url") val mediumUrl: String,
    @SerialName("small_url") val smallUrl: String,
    @SerialName("original_url") val originalUrl: String,
    @SerialName("original_format") val originalFormat: String = "",
    val width: Long = 0,
    val height: Long = 0,
    val color: String,
    @SerialName("original_size") val originalSize: Long = 0,
)

@Serializable
data class RawCover(
    val url: String,
    @SerialName("medium_url") val mediumUrl: String,
    @SerialName("small_url") val smallUrl: String,
    @SerialName("original_url") val originalUrl: String,
    @SerialName("original_format") val originalFormat: String = "",
    val width: Long = 0,
    val height: Long = 0,
    val color: String,
    @SerialName("original_size") val originalSize: Long = 0,
)

@Serializable
data class PlayLog(
    @SerialName("video_id") val videoId: Long,
    val paramId: Long = 0,
    val paramType: String,
)

@Serializable
data class Status(
    @SerialName("can_play") val canPlay: Boolean,
)

@Serializable
data class RecReason(
    val type: Long,
    val id: String,
    val text: String,
)

@Serializable
data class Category(
    val id: Long,
    @SerialName("id_str") val idStr: String,
    val title: String,
    val uri: String,
    val type: String,
    val total: Long,
    val list: List<App>,
    @SerialName("event_log") val eventLog: EventLog,
    val user: User,
    val cover: Cover,
)


@Serializable
data class User(
    val id: Long,
    val name: String,
    val avatar: String,
    @SerialName("medium_avatar") val mediumAvatar: String,
    val gender: String,
    val store: String,
    val intro: String,
    @SerialName("is_certified") val isCertified: Boolean,
    @SerialName("is_anonymous") val isAnonymous: Boolean,
    @SerialName("is_ban") val isBan: Boolean,
    @SerialName("is_deactivated") val isDeactivated: Boolean,
    val stat: Stat,
    val log: Log,
    @SerialName("event_log") val eventLog: EventLog,
    @SerialName("i_wear_badges") val iWearBadges: List<IWearBadge>? = null,
    @SerialName("is_silent") val isSilent: Boolean,
)

@Serializable
data class IWearBadge(
    val id: Long,
    val title: String,
    val description: String,
    @SerialName("earn_link") val earnLink: String = "",
    @SerialName("congratulate_description") val congratulateDescription: String = "",
    @SerialName("congratulate_button_text") val congratulateButtonText: String = "",
    @SerialName("congratulate_link") val congratulateLink: String = "",
    @SerialName("small_image") val smallImage: SmallImage,
    @SerialName("middle_image") val middleImage: MiddleImage,
    @SerialName("large_image") val largeImage: LargeImage,
    @SerialName("is_earned") val isEarned: Boolean,
    @SerialName("is_wear") val isWear: Boolean,
    @SerialName("can_earn") val canEarn: Boolean,
    @SerialName("earned_count") val earnedCount: Long,
    @SerialName("earned_time") val earnedTime: Long,
)

@Serializable
data class SmallImage(
    val url: String,
    @SerialName("medium_url") val mediumUrl: String,
    @SerialName("small_url") val smallUrl: String,
    @SerialName("original_url") val originalUrl: String,
    @SerialName("original_format") val originalFormat: String? = null,
    val width: Long = 0,
    val height: Long = 0,
    val color: String,
    @SerialName("original_size") val originalSize: Long = 0,
)

@Serializable
data class MiddleImage(
    val url: String,
    @SerialName("medium_url") val mediumUrl: String,
    @SerialName("small_url") val smallUrl: String,
    @SerialName("original_url") val originalUrl: String,
    @SerialName("original_format") val originalFormat: String? = null,
    val width: Long = 0,
    val height: Long = 0,
    val color: String,
    @SerialName("original_size") val originalSize: Long = 0,
)

@Serializable
data class LargeImage(
    val url: String,
    @SerialName("medium_url") val mediumUrl: String,
    @SerialName("small_url") val smallUrl: String,
    @SerialName("original_url") val originalUrl: String,
    @SerialName("original_format") val originalFormat: String? = null,
    val width: Long = 0,
    val height: Long = 0,
    val color: String,
    @SerialName("original_size") val originalSize: Long = 0,
)

@Serializable
data class Cover(
    val url: String,
    @SerialName("medium_url") val mediumUrl: String,
    @SerialName("small_url") val smallUrl: String,
    @SerialName("original_url") val originalUrl: String,
    @SerialName("original_format") val originalFormat: String? = null,
    val width: Long = 0,
    val height: Long = 0,
    val color: String,
    @SerialName("original_size") val originalSize: Long = 0,
)

