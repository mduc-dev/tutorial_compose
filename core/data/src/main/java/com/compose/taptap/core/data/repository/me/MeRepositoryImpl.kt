package com.compose.taptap.core.data.repository.me

import com.compose.taptap.core.data.datasource.local.LocalStorage
import com.compose.taptap.core.data.repository.base.BaseRepository
import com.compose.taptap.core.domain.repository.MeRepository
import com.compose.taptap.core.model.MetricData
import com.compose.taptap.core.model.UserProfileData
import com.compose.taptap.core.network.service.TapTapService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MeRepositoryImpl(
    private val tapTapService: TapTapService,
    private val dispatcher: CoroutineDispatcher,
    private val localStorage: LocalStorage,
    private val json: Json
) : BaseRepository(), MeRepository {

    companion object {
        private const val KEY_USER_PROFILE = "user_profile_cache"
    }

    override fun getUserProfile(): Flow<UserProfileData> = flow {
        // 1. Emit cached data first (Fast)
        val cachedJson = localStorage.getString(KEY_USER_PROFILE)
        if (!cachedJson.isNullOrBlank()) {
            try {
                val cachedProfile = json.decodeFromString<UserProfileData>(cachedJson)
                emit(cachedProfile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 2. Fetch from "Network" (Simulated with Mock Data due to missing Auth Headers)
        try {
            // Simulate network latency to demonstrate caching benefit
            delay(800)

            // In the future, this will be: val response = tapTapService.getUserProfile()
            // For now, use local mock data:
            val data = mockUserProfile

            // OPTIMIZATION: Emit to UI immediately! 
            // Don't wait for JSON encoding or Disk IO.
            emit(data)

            // Save to cache asynchronously/afterwards
            try {
                localStorage.putString(KEY_USER_PROFILE, json.encodeToString(data))
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            // If cache exists, we suppress error. If not, throw.
            if (cachedJson == null) throw e
        }
    }.flowOn(dispatcher)

    // Temporary Mock Data (Moved from ViewModel)
    private val mockUserProfile = UserProfileData(
        id = 364066371,
        store = "other",
        name = "Duc Nguyen",
        nickname = "Duc Nguyen",
        nameIsUnderReview = false,
        avatar = "https://img3.tapimg.net/third_avatars/bcb24ff1a1a726fd64f6d3653b2da3cb.png?imageMogr2/auto-orient/strip/thumbnail/!270x270r/gravity/Center/crop/270x270/format/jpg/interlace/1/quality/80&t=1",
        mediumAvatar = "https://img3.tapimg.net/third_avatars/bcb24ff1a1a726fd64f6d3653b2da3cb.png?imageMogr2/auto-orient/strip/thumbnail/!180x180r/gravity/Center/crop/180x180/format/jpg/interlace/1/quality/80&t=1",
        intro = "",
        email = null,
        socials = emptyList(),
        stats = MetricData(
            followingCount = 39,
            fansCount = 0,
            voteupCount = 1,
            appWishlistCount = 8,
            playedAppCount = 16,
            playingAppCount = 0
        )
    )
}
