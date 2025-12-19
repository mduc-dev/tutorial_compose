package com.compose.taptap.feature.me

import androidx.lifecycle.viewModelScope
import com.compose.taptap.core.designsystem.util.loading
import com.compose.taptap.core.designsystem.util.loadingSuccess
import com.compose.taptap.core.domain.usecases.me.GetUserProfileUseCase
import com.compose.taptap.core.model.BadgeWearInfoData
import com.compose.taptap.core.model.FeedItem
import com.compose.taptap.core.model.UserAppStatusItem
import com.compose.taptap.core.preview.PreviewUtils.mockBadges
import com.compose.taptap.core.preview.PreviewUtils.mockMyGames
import com.compose.taptap.core.preview.PreviewUtils.mockPosts
import com.compose.taptap.core.viewmodel.BaseViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class MeViewModel(
    getUserProfileUseCase: GetUserProfileUseCase
) : BaseViewModel() {
    // Mocking data now cause need header authorization in request API
    // Data State (LoadingResult pattern like GameViewModel's placeholder/search)
    val userProfileState = getUserProfileUseCase.execute(Unit)
        .map { loadingSuccess(it) }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = loading()
        )

    // UI configuration + Mock Data
    private val _uiState = MutableStateFlow(
        MeUiState(
            myGames = mockMyGames.toImmutableList(),
            badges = mockBadges,
            posts = mockPosts.toImmutableList()
        )
    )
    val uiState = _uiState.asStateFlow()
}

val tabYou = persistentListOf("Posts", "Saved", "Drafts")
val chipYou = persistentListOf("All", "Gamelists", "Articles", "Videos")
/**
 * Represents the UI state for the Me screen configuration.
 * This is a pure data class with no business logic.
 */
data class MeUiState(
    val selectedTab: Int = 0,
    val selectedFilter: String = "All",
    val myGames: ImmutableList<UserAppStatusItem> = persistentListOf(),
    val badges: BadgeWearInfoData? = null,
    val posts: ImmutableList<FeedItem> = persistentListOf()
)
