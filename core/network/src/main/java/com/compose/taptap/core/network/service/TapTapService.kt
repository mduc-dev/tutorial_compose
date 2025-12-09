package com.compose.taptap.core.network.service

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

interface TapTapService {
    suspend fun getGames(cursor: String? = null): GameResponse

    suspend fun getPlayGames(cursor: String? = null): PlayGameResponse

    suspend fun getSearchPlaceholder(): SearchResponse

    suspend fun getRandomInstantGame(): InstantGameRandomResponse

    suspend fun getMetricAccount(): MetricAccountResponse

    suspend fun getNewEarnBadge(): EarnBadgeResponse

    suspend fun getMiniMultiGet(): MiniMultiGetResponse

    suspend fun getUserAppByIdentifiers(): UserAppByIdentifiersResponse

    suspend fun getBadgeWearInfoByMe(): BadgeWearInfoResponse

    suspend fun getFeedByMe(): FeedByMeResponse

    suspend fun getBadgeListByMe(): BadgeListResponse

    suspend fun getUserProfile(): UserProfileResponse

    suspend fun getTermBrand(): TermBrandResponse

    suspend fun getCreationFavorite(): CreationFavoriteResponse

    suspend fun getCreationVote(): CreationVoteResponse

    suspend fun getUserAppStatus(): UserAppStatusResponse
}
