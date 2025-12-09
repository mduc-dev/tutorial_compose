package com.compose.taptap.core.network.service

import com.compose.taptap.core.network.di.BUILDCONFIG
import com.compose.taptap.core.network.model.BadgeListResponse
import com.compose.taptap.core.network.model.BadgeWearInfoResponse
import com.compose.taptap.core.network.model.CreationFavoriteResponse
import com.compose.taptap.core.network.model.CreationVoteResponse
import com.compose.taptap.core.network.model.EarnBadgeResponse
import com.compose.taptap.core.network.model.FeedByMeResponse
import com.compose.taptap.core.network.model.GameResponse
import com.compose.taptap.core.network.model.InstantGameRandomResponse
import com.compose.taptap.core.network.model.MetricAccountResponse
import com.compose.taptap.core.network.model.MiniMultiGetResponse
import com.compose.taptap.core.network.model.PlayGameResponse
import com.compose.taptap.core.network.model.SearchResponse
import com.compose.taptap.core.network.model.TermBrandResponse
import com.compose.taptap.core.network.model.UserAppByIdentifiersResponse
import com.compose.taptap.core.network.model.UserAppStatusResponse
import com.compose.taptap.core.network.model.UserProfileResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TapTapClient(private val httpClient: HttpClient) : TapTapService {
    private val gameXua: String by lazy { BUILDCONFIG.newXUA() }
    private val playXua: String by lazy { BUILDCONFIG.newXUA() }

    override suspend fun getGames(cursor: String?): GameResponse {
        val url = cursor?.takeIf { it.isNotBlank() }?.let { resolveCursorUrl(it, gameXua) }
            ?: BUILDCONFIG.gameUrl(xua = gameXua)
        return httpClient.get(url).body()
    }

    override suspend fun getPlayGames(cursor: String?): PlayGameResponse {
        val url = cursor?.takeIf { it.isNotBlank() }?.let { resolveCursorUrl(it, playXua) }
            ?: BUILDCONFIG.instantPlay(xua = playXua)
        return httpClient.get(url).body()
    }

    override suspend fun getSearchPlaceholder(): SearchResponse {
        return httpClient.get(BUILDCONFIG.searchPlaceholder()).body()
    }

    override suspend fun getRandomInstantGame(): InstantGameRandomResponse {
        return httpClient.get(BUILDCONFIG.randomInstantPlayGame(xua = playXua)).body()
    }

    override suspend fun getMetricAccount(): MetricAccountResponse {
        return httpClient.get(BUILDCONFIG.countMetricAccount(xua = playXua)).body()
    }

    override suspend fun getNewEarnBadge(): EarnBadgeResponse {
        return httpClient.get(BUILDCONFIG.newErnBadge(xua = playXua)).body()
    }

    override suspend fun getMiniMultiGet(): MiniMultiGetResponse {
        return httpClient.get(BUILDCONFIG.miniMultiGet(xua = playXua)).body()
    }

    override suspend fun getUserAppByIdentifiers(): UserAppByIdentifiersResponse {
        return httpClient.get(BUILDCONFIG.userAppByIdentifiers(xua = playXua)).body()
    }

    override suspend fun getBadgeWearInfoByMe(): BadgeWearInfoResponse {
        return httpClient.get(BUILDCONFIG.badgeWearInfoByMe(xua = playXua)).body()
    }

    override suspend fun getFeedByMe(): FeedByMeResponse {
        return httpClient.get(BUILDCONFIG.feedByMe(xua = playXua)).body()
    }

    override suspend fun getBadgeListByMe(): BadgeListResponse {
        return httpClient.get(BUILDCONFIG.badgeListByMe(xua = playXua)).body()
    }

    override suspend fun getUserProfile(): UserProfileResponse {
        return httpClient.get(BUILDCONFIG.userProfileUrl(xua = playXua)).body()
    }

    override suspend fun getTermBrand(): TermBrandResponse {
        return httpClient.get(BUILDCONFIG.termBrand(xua = playXua)).body()
    }

    override suspend fun getCreationFavorite(): CreationFavoriteResponse {
        return httpClient.get(BUILDCONFIG.creationFavorite(xua = playXua)).body()
    }

    override suspend fun getCreationVote(): CreationVoteResponse {
        return httpClient.get(BUILDCONFIG.creationVote(xua = playXua)).body()
    }

    override suspend fun getUserAppStatus(): UserAppStatusResponse {
        return httpClient.get(BUILDCONFIG.userAppStatus(xua = playXua)).body()
    }


    private fun resolveCursorUrl(cursor: String, xua: String): String {
        val trimmed = cursor.trim()
        val resolved = when {
            trimmed.startsWith("http", ignoreCase = true) -> trimmed
            trimmed.startsWith("/") -> "${BUILDCONFIG.BASE_URL}$trimmed"
            else -> "${BUILDCONFIG.BASE_URL}/$trimmed"
        }
        return BUILDCONFIG.ensureXua(resolved, xua)
    }
}
