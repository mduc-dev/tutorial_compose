package com.compose.taptap.core.network.service

import com.compose.taptap.core.network.model.GameResponse
import com.compose.taptap.core.network.model.PlayGameResponse
import com.compose.taptap.core.network.model.SearchResponse

interface TapTapService {
    suspend fun getGames(): Result<GameResponse>

    suspend fun getPlayGames(): Result<PlayGameResponse>

    suspend fun getSearchPlaceholder(): Result<SearchResponse>
}