package com.compose.taptap.feature.me


import com.compose.taptap.core.domain.usecases.me.GetUserProfileUseCase
import com.compose.taptap.core.model.UserProfileData
import com.compose.taptap.core.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class MeViewModel(
    getUserProfileUseCase: GetUserProfileUseCase
) : BaseViewModel() {

    val uiState = getUserProfileUseCase.execute(Unit)
        .map<UserProfileData, MeUiState> { MeUiState.Success(it) }
        .onStart { emit(MeUiState.Loading) }
        .catch { emit(MeUiState.Error) }
        .stateInViewModel(
            initial = MeUiState.Loading,
            started = SharingStarted.Lazily
        )
}

sealed interface MeUiState {
    data object Loading : MeUiState
    data class Success(val data: UserProfileData) : MeUiState
    data object Error : MeUiState
}


