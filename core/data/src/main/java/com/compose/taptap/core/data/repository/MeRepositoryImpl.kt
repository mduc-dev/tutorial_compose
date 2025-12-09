
package com.compose.taptap.core.data.repository

import com.compose.taptap.core.domain.repository.MeRepository
import com.compose.taptap.core.model.UserProfileData
import com.compose.taptap.core.network.service.TapTapService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MeRepositoryImpl(
    private val client: TapTapService
) : MeRepository {
    override fun getUserProfile(): Flow<UserProfileData> = flow {
        val response = client.getUserProfile()
        if (response.success == true && response.data != null) {
            emit(response.data!!)
        } else {
            // Can throw or emit null, but for now assuming data or error
            throw Exception("Failed to fetch user profile")
        }
    }
}
