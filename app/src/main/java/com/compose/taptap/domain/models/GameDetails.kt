package com.compose.taptap.domain.models

data class GameDetails(
    val id: Int,

    val title: String? = null,

    val video: Boolean? = null,

    val posterPath: String? = null,

    val voteAverage: Double? = null,

    val voteCount: Int? = null,

    val category: String? = null,

    val releaseDate: String? = null,
)