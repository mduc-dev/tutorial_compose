package com.compose.taptap.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by duc on 09/12/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
@Serializable
data class MetricData(
    @SerialName("created_days") val createdDays: Int? = null,
    @SerialName("favorite_app_count") val favoriteAppCount: Int? = null,
    @SerialName("favorite_event_count") val favoriteEventCount: Int? = null,
    @SerialName("favorite_topic_count") val favoriteTopicCount: Int? = null,
    @SerialName("favorite_video_count") val favoriteVideoCount: Int? = null,
    @SerialName("favorite_moment_count") val favoriteMomentCount: Int? = null,
    @SerialName("favorite_story_count") val favoriteStoryCount: Int? = null,
    @SerialName("favorite_hashtag_count") val favoriteHashtagCount: Int? = null,
    @SerialName("favorite_product_count") val favoriteProductCount: Int? = null,
    @SerialName("history_app_count") val historyAppCount: Int? = null,
    @SerialName("history_topic_count") val historyTopicCount: Int? = null,
    @SerialName("history_video_count") val historyVideoCount: Int? = null,
    @SerialName("want_app_count") val wantAppCount: Int? = null,
    @SerialName("playing_app_count") val playingAppCount: Int? = null,
    @SerialName("played_app_count") val playedAppCount: Int? = null,
    @SerialName("user_app_status_count") val userAppStatusCount: Int? = null,
    @SerialName("creation_post_voteup_count") val creationPostVoteupCount: Int? = null,
    @SerialName("created_creation_post_count") val createdCreationPostCount: Int? = null,
    @SerialName("favorite_creation_post_count") val favoriteCreationPostCount: Int? = null,
    @SerialName("created_review_count") val createdReviewCount: Int? = null,
    @SerialName("created_topic_count") val createdTopicCount: Int? = null,
    @SerialName("created_video_count") val createdVideoCount: Int? = null,
    @SerialName("created_moment_count") val createdMomentCount: Int? = null,
    @SerialName("created_album_count") val createdAlbumCount: Int? = null,
    @SerialName("created_story_count") val createdStoryCount: Int? = null,
    @SerialName("created_post_count") val createdPostCount: Int? = null,
    @SerialName("voteup_count") val voteupCount: Int? = null,
    @SerialName("votefunny_count") val votefunnyCount: Int? = null,
    @SerialName("be_voted_up_review_count") val beVotedUpReviewCount: Int? = null,
    @SerialName("be_voted_up_moment_count") val beVotedUpMomentCount: Int? = null,
    @SerialName("be_favorited_count") val beFavoritedCount: Int? = null,
    @SerialName("forum_count") val forumCount: Int? = null,
    @SerialName("following_developer_count") val followingDeveloperCount: Int? = null,
    @SerialName("following_app_count") val followingAppCount: Int? = null,
    @SerialName("following_group_count") val followingGroupCount: Int? = null,
    @SerialName("following_hashtag_count") val followingHashtagCount: Int? = null,
    @SerialName("following_craft_count") val followingCraftCount: Int? = null,
    @SerialName("played_count") val playedCount: Int? = null,
    @SerialName("played_spent") val playedSpent: Long? = null,
    @SerialName("reserved_count") val reservedCount: Int? = null,
    @SerialName("cloud_game_played_count") val cloudGamePlayedCount: Int? = null,
    @SerialName("following_console_game_app_count") val followingConsoleGameAppCount: Int? = null,
    @SerialName("badges_count") val badgesCount: Int? = null,
    @SerialName("fans_count") val fansCount: Int? = null,
    @SerialName("following_count") val followingCount: Int? = null,
    @SerialName("spent_tips") val spentTips: String? = null,
    @SerialName("reserved_total_count") val reservedTotalCount: Int? = null,
    @SerialName("app_achievement_count") val appAchievementCount: Int? = null,
    @SerialName("notification_unread_total") val notificationUnreadTotal: Int? = null,
    @SerialName("message_unread_total") val messageUnreadTotal: Int? = null,
    @SerialName("notification_inbox_unread_total") val notificationInboxUnreadTotal: Int? = null,
    @SerialName("notification_interactive_unread_total") val notificationInteractiveUnreadTotal: Int? = null,
    @SerialName("notification_up_unread_total") val notificationUpUnreadTotal: Int? = null,
    @SerialName("notification_follow_unread_total") val notificationFollowUnreadTotal: Int? = null,
    @SerialName("notification_system_unread_total") val notificationSystemUnreadTotal: Int? = null,
    @SerialName("purchased_app_count") val purchasedAppCount: Int? = null,
    @SerialName("related_app_count") val relatedAppCount: Int? = null,
    @SerialName("app_wishlist_count") val appWishlistCount: Int? = null,
    @SerialName("friend_count") val friendCount: Int? = null
)
