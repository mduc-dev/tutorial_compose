package com.compose.taptap.core.network.service

import com.compose.taptap.core.network.model.GameResponse
import com.compose.taptap.core.network.model.PlayGameResponse
import com.compose.taptap.core.network.model.SearchResponse

interface TapTapService {
    suspend fun getGames(cursor: String? = null): GameResponse

    suspend fun getPlayGames(cursor: String? = null): PlayGameResponse

    suspend fun getSearchPlaceholder(): SearchResponse
}
