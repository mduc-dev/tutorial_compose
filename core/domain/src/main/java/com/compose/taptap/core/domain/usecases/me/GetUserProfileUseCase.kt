
package com.compose.taptap.core.domain.usecases.me

import com.compose.taptap.core.domain.repository.MeRepository
import com.compose.taptap.core.domain.usecases.base.BaseFlowUseCase
import com.compose.taptap.core.model.UserProfileData
import kotlinx.coroutines.flow.Flow

class GetUserProfileUseCase(
    private val repository: MeRepository
) : BaseFlowUseCase<Unit, UserProfileData>() {
    override fun execute(parameters: Unit): Flow<UserProfileData> =
        repository.getUserProfile()
}
