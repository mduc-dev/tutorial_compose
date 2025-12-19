
package com.compose.taptap.core.data.repository

import com.compose.taptap.core.data.repository.base.BaseRepository
import com.compose.taptap.core.domain.repository.MeRepository
import com.compose.taptap.core.model.UserProfileData
import com.compose.taptap.core.network.service.TapTapService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class MeRepositoryImpl(
    private val tapTapService: TapTapService,
    private val dispatcher: CoroutineDispatcher,
) : BaseRepository(), MeRepository {
    override fun getUserProfile(): Flow<UserProfileData> = safeFlow(dispatcher) {
        val response = tapTapService.getUserProfile()
        if (response.success == true && response.data != null) {
            response.data!!
        } else {
            // Can throw or emit null, but for now assuming data or error
            throw Exception("Failed to fetch user profile")
        }
    }
}
