package com.compose.taptap.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */
typealias InstantGame = PaginationData<InstantGameItem>

@Serializable
data class InstantGameItem(
    val identification: String,
    val type: Int,
    val title: String,
    val cover: GameImage,
    val icon: GameImage,
    val uri: String,
    @SerialName("app_id") val appId: Long? = null,
    val stats: Stats? = null,
    val subtitle: String = "",
    @SerialName("miniapp_preload_resource") val miniappPreloadResource: MiniAppPreloadResource? = null
)

@Serializable
data class GameImage(
    val url: String,
    @SerialName("medium_url") val mediumUrl: String,
    @SerialName("small_url") val smallUrl: String,
    @SerialName("original_url") val originalUrl: String,
    @SerialName("original_format") val originalFormat: String,
    val width: Int,
    val height: Int,
    val color: String,
    @SerialName("original_size") val originalSize: Long
)

@Serializable
data class Stats(
    val score: String
)

@Serializable
data class MiniAppPreloadResource(
    @SerialName("miniapp_id") val miniAppId: String,
    val version: Version,
    @SerialName("entry_package") val entryPackage: EntryPackage,
    @SerialName("device_orientation") val deviceOrientation: String
)

@Serializable
data class Version(
    @SerialName("version_id") val versionId: Long,
    val version: String,
    @SerialName("package_id") val packageId: Long
)

@Serializable
data class EntryPackage(
    val url: String,
    @SerialName("size_bytes") val sizeBytes: Long,
    val md5: String
)
