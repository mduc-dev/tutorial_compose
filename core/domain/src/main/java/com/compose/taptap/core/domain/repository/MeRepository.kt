
package com.compose.taptap.core.domain.repository

import com.compose.taptap.core.model.UserProfileData
import kotlinx.coroutines.flow.Flow

interface MeRepository {
    fun getUserProfile(): Flow<UserProfileData>
}
