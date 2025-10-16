package com.compose.taptap.domain.utils

import com.compose.taptap.data.model.ListItem

/**
 * Created by duc on 16/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */


val ListItem.displayTitle: String
    get() = app?.title ?: "Unknown Game"

val ListItem.displayRating: String
    get() = app?.stat?.rating?.score?.takeIf { it.isNotBlank() } ?: "--"

val ListItem.displayIconUrl: String?
    get() = app?.icon?.smallUrl

val ListItem.displayBannerUrl: String?
    get() = app?.banner?.mediumUrl

val ListItem.displayRecReason: String?
    get() = recReason?.text

val ListItem.displayTags: List<String>
    get() = app?.tags?.map { it.value }?.filter { it.isNotBlank() }?.take(3) ?: emptyList()

val ListItem.displayPlatforms: List<String>
    get() = app?.supportedPlatforms?.map { it.key } ?: emptyList()

val ListItem.displayTagLineItems: List<String>
    get() {
        val tags = displayTags
        val platforms = displayPlatforms
        return when {
            tags.size >= 3 -> tags
            tags.isNotEmpty() -> tags + platforms
            platforms.isNotEmpty() -> platforms
            else -> emptyList()
        }
    }