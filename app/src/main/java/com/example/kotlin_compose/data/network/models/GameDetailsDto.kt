package com.example.kotlin_compose.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDetailsDto(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String? = null,

    @SerialName("video")
    val video: Boolean? = null,

    @SerialName("posterPath")
    val posterPath: String? = null,

    @SerialName("voteAverage")
    val voteAverage: Double? = null,

    @SerialName("voteCount")
    val voteCount: Int? = null,

    @SerialName("category")
    val category: String? = null,

    @SerialName("releaseDate")
    val releaseDate: String? = null,
)