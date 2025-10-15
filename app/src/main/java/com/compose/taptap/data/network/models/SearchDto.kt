package com.compose.taptap.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class SearchPlaceHolder(
    val data: DataContainer, val now: Long, val success: Boolean
) {
    fun firstTextOrDefault(default: String = "Discover Superb Games"): String {
        return data.list.firstOrNull()?.text ?: default
    }
}

@Serializable
data class DataContainer(
    val list: List<Item>
)

@Serializable
data class Item(
    val kw: String,
    val text: String,
)
