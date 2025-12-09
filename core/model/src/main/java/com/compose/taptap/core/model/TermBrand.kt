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
data class TermBrandData(
    @SerialName("actions") val actions: JsonElement? = null,
    @SerialName("captcha_url") val captchaUrl: String? = null,
    @SerialName("contact_discord") val contactDiscord: String? = null,
    @SerialName("contact_email") val contactEmail: String? = null,
    @SerialName("delete_account_dialog") val deleteAccountDialog: String? = null,
    @SerialName("download_lines") val downloadLines: String? = null,
    @SerialName("editor_config_v2") val editorConfigV2: String? = null,
    @SerialName("editor_limit_config") val editorLimitConfig: String? = null,
    @SerialName("email_login_type") val emailLoginType: Int? = null,
    @SerialName("feedback_rebranding") val feedbackRebranding: String? = null,
    @SerialName("frozen_config") val frozenConfig: String? = null,
    @SerialName("game_code_hint") val gameCodeHint: String? = null,
    @SerialName("h5_game_configs") val h5GameConfigs: String? = null,
    @SerialName("index_ad") val indexAd: Int? = null,
    @SerialName("instant_game_preview_dialog") val instantGamePreviewDialog: String? = null,
    @SerialName("ip") val ip: String? = null,
    @SerialName("iso_country_code") val isoCountryCode: String? = null,
    @SerialName("list_region_t1") val listRegionT1: String? = null,
    @SerialName("list_sorts") val listSorts: String? = null,
    @SerialName("lite_download_enable") val liteDownloadEnable: String? = null,
    @SerialName("modify_web_link") val modifyWebLink: String? = null,
    @SerialName("myWalletEntry") val myWalletEntry: String? = null,
    @SerialName("network_monitor_paths") val networkMonitorPaths: String? = null,
    @SerialName("phone_country_code") val phoneCountryCode: String? = null,
    @SerialName("qr_prefixs") val qrPrefixs: String? = null,
    @SerialName("sandbox_mode") val sandboxMode: Boolean? = null,
    @SerialName("show_birthday_region") val showBirthdayRegion: String? = null,
    @SerialName("show_home_login_skip") val showHomeLoginSkip: String? = null,
    @SerialName("show_manage_payment") val showManagePayment: String? = null,
    @SerialName("socials") val socials: List<String>? = null,
    @SerialName("socials_config") val socialsConfig: List<TermSocialConfig>? = null,
    @SerialName("sorted_game_categories") val sortedGameCategories: String? = null,
    @SerialName("upcoming_game_platforms") val upcomingGamePlatforms: String? = null,
    @SerialName("uploadVideoSize") val uploadVideoSize: String? = null,
    @SerialName("uri_verified_uri_config") val uriVerifiedUriConfig: String? = null,
    @SerialName("video_loudness_level") val videoLoudnessLevel: String? = null,
    @SerialName("video_play_track_index") val videoPlayTrackIndex: String? = null,
    @SerialName("webp_global_config") val webpGlobalConfig: String? = null
)

@Serializable
data class TermSocialConfig(
    @SerialName("provider") val provider: String? = null,
    @SerialName("login") val login: Boolean? = null,
    @SerialName("bind") val bind: Boolean? = null
)
