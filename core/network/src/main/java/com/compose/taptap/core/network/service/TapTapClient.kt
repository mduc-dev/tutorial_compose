package com.compose.taptap.core.network.service

import com.compose.taptap.core.network.di.BUILDCONFIG
import com.compose.taptap.core.network.model.GameResponse
import com.compose.taptap.core.network.model.PlayGameResponse
import com.compose.taptap.core.network.model.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TapTapClient(private val httpClient: HttpClient) : TapTapService {
    override suspend fun getGames(): Result<GameResponse> {
        return httpClient.get(BUILDCONFIG.gameUrl()).body()
    }

    override suspend fun getPlayGames(): Result<PlayGameResponse> {
        return httpClient.get(BUILDCONFIG.instantPlay()).body()
    }

    override suspend fun getSearchPlaceholder(): Result<SearchResponse> {
        return httpClient.get(BUILDCONFIG.instantPlay()).body()
    }
}