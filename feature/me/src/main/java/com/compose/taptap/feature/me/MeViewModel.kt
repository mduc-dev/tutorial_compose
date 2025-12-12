package com.compose.taptap.feature.me

import androidx.lifecycle.viewModelScope
import com.compose.taptap.core.designsystem.util.loading
import com.compose.taptap.core.designsystem.util.loadingSuccess
import com.compose.taptap.core.domain.usecases.me.GetUserProfileUseCase
import com.compose.taptap.core.model.AppDetail
import com.compose.taptap.core.model.BadgeWearInfoData
import com.compose.taptap.core.model.FeedItem
import com.compose.taptap.core.model.FeedStat
import com.compose.taptap.core.model.FeedUser
import com.compose.taptap.core.model.Icon
import com.compose.taptap.core.model.ListFields
import com.compose.taptap.core.model.MetricData
import com.compose.taptap.core.model.PostDetail
import com.compose.taptap.core.model.UserAppStatusItem
import com.compose.taptap.core.model.UserProfileData
import com.compose.taptap.core.viewmodel.BaseViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class MeViewModel(
    getUserProfileUseCase: GetUserProfileUseCase
) : BaseViewModel() {
    // Mocking data now cause need header authorization in request API
    // Data State (LoadingResult pattern like GameViewModel's placeholder/search)
    val userProfileState = kotlinx.coroutines.flow.flowOf(
        loadingSuccess(
            UserProfileData(
                id = 364066371,
                store = "other",
                name = "Duc Nguyen",
                nickname = "Duc Nguyen",
                nameIsUnderReview = false,
                avatar = "https://img3.tapimg.net/third_avatars/bcb24ff1a1a726fd64f6d3653b2da3cb.png?imageMogr2/auto-orient/strip/thumbnail/!270x270r/gravity/Center/crop/270x270/format/jpg/interlace/1/quality/80&t=1",
                mediumAvatar = "https://img3.tapimg.net/third_avatars/bcb24ff1a1a726fd64f6d3653b2da3cb.png?imageMogr2/auto-orient/strip/thumbnail/!180x180r/gravity/Center/crop/180x180/format/jpg/interlace/1/quality/80&t=1",
                intro = "",
                email = null, // simplified
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
        )
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = loading()
        )

    private val mockMyGames = persistentListOf(
        UserAppStatusItem(
            app = AppDetail(
                id = 33622025,
                identifier = "com.supercell.moco",
                title = "mo.co",
                icon = Icon(
                    url = "https://img.tapimg.net/market/images/508c4f098eacb645b22cf73f773a16ab.png/appicon?t=1",
                    mediumUrl = "",
                    smallUrl = "",
                    originalUrl = "",
                    originalFormat = "",
                    width = 0,
                    height = 0,
                    color = ""
                )
            )
        ),
        UserAppStatusItem(
            app = AppDetail(
                id = 217247,
                identifier = "com.netease.harrypotter.na",
                title = "Harry Potter: Magic Awakened™",
                icon = Icon(
                    url = "https://img.tapimg.net/market/images/bf1c1a71ec570cf84b3dc35fd0f13934.jpg/appicon?t=1",
                    mediumUrl = "",
                    smallUrl = "",
                    originalUrl = "",
                    originalFormat = "",
                    width = 0,
                    height = 0,
                    color = ""
                )
            )
        ),
        UserAppStatusItem(
            app = AppDetail(
                id = 79242,
                identifier = "com.netease.onmyoji.gb",
                title = "Onmyoji",
                icon = Icon(
                    url = "https://img.tapimg.net/market/images/00b4aa12a54d2522b8d364b098024a7d.png/appicon?t=1",
                    mediumUrl = "",
                    smallUrl = "",
                    originalUrl = "",
                    originalFormat = "",
                    width = 0,
                    height = 0,
                    color = ""
                )
            )
        ),
        UserAppStatusItem(
            app = AppDetail(
                id = 231549,
                identifier = "com.colossi.survival.gladiators",
                title = "Gladiators: Survival in Rome",
                icon = Icon(
                    url = "https://img.tapimg.net/market/images/867591bf1c6266390cb612317dbb6ec4.png/appicon?t=1",
                    mediumUrl = "",
                    smallUrl = "",
                    originalUrl = "",
                    originalFormat = "",
                    width = 0,
                    height = 0,
                    color = ""
                )
            )
        ),
        UserAppStatusItem(
            app = AppDetail(
                id = 231392,
                identifier = "com.duoyihk.m3m1us",
                title = "Gunfire Reborn",
                icon = Icon(
                    url = "https://img.tapimg.net/market/images/33034f4ee6e2b61f3af9ecca8eced76b.png/appicon?t=1",
                    mediumUrl = "",
                    smallUrl = "",
                    originalUrl = "",
                    originalFormat = "",
                    width = 0,
                    height = 0,
                    color = ""
                )
            )
        ),
        UserAppStatusItem(
            app = AppDetail(
                id = 232717,
                identifier = "com.smilegate.tto.stove.google",
                title = "Teetiny",
                icon = Icon(
                    url = "https://img.tapimg.net/market/images/653e8e0290cdf552dceb34c1bc584f76.jpg/appicon?t=1",
                    mediumUrl = "",
                    smallUrl = "",
                    originalUrl = "",
                    originalFormat = "",
                    width = 0,
                    height = 0,
                    color = ""
                )
            )
        ),
        UserAppStatusItem(
            app = AppDetail(
                id = 232721,
                identifier = "com.ciyuanshaonu.rpg.en.qooapp",
                title = "Clash of sky",
                icon = Icon(
                    url = "https://img.tapimg.net/market/images/811148edb53b62cb6a89270c93404a47.jpg/appicon?t=1",
                    mediumUrl = "",
                    smallUrl = "",
                    originalUrl = "",
                    originalFormat = "",
                    width = 0,
                    height = 0,
                    color = ""
                )
            )
        ),
        UserAppStatusItem(
            app = AppDetail(
                id = 223381,
                identifier = "",
                title = "BarbarQ 2",
                icon = Icon(
                    url = "https://img.tapimg.net/market/images/73760a86544695033d08e35f9eb7b69c.png/appicon?t=1",
                    mediumUrl = "",
                    smallUrl = "",
                    originalUrl = "",
                    originalFormat = "",
                    width = 0,
                    height = 0,
                    color = ""
                )
            )
        ),
        UserAppStatusItem(
            app = AppDetail(
                id = 232971,
                identifier = "com.bandainamcoent.saovsww",
                title = "Sword Art Online VS",
                icon = Icon(
                    url = "https://img.tapimg.net/market/images/901eccca96c885f448635e7447e81d21.png/appicon?t=1",
                    mediumUrl = "",
                    smallUrl = "",
                    originalUrl = "",
                    originalFormat = "",
                    width = 0,
                    height = 0,
                    color = ""
                )
            )
        ),
        UserAppStatusItem(
            app = AppDetail(
                id = 163760,
                identifier = "com.blizzard.diablo.immortal",
                title = "Diablo Immortal",
                icon = Icon(
                    url = "https://img.tapimg.net/market/images/24e98451c5ab25bc600b4a7e4c2bac07.png/appicon?t=1",
                    mediumUrl = "",
                    smallUrl = "",
                    originalUrl = "",
                    originalFormat = "",
                    width = 0,
                    height = 0,
                    color = ""
                )
            )
        )
    )

    private val mockBadges = BadgeWearInfoData(
        total = 0,
        info = null
    )

    private val mockPosts = persistentListOf(
        FeedItem(
            type = "post",
            identification = "post:7456704",
            post = PostDetail(
                idStr = "7456704",
                id = 7456704,
                user = FeedUser(
                    id = 364066371,
                    name = "Duc Nguyen",
                    avatar = "https://img3.tapimg.net/third_avatars/bcb24ff1a1a726fd64f6d3653b2da3cb.png?imageMogr2/auto-orient/strip/thumbnail/!270x270r/gravity/Center/crop/270x270/format/jpg/interlace/1/quality/80",
                    mediumAvatar = "https://img3.tapimg.net/third_avatars/bcb24ff1a1a726fd64f6d3653b2da3cb.png?imageMogr2/auto-orient/strip/thumbnail/!180x180r/gravity/Center/crop/180x180/format/jpg/interlace/1/quality/40"
                ),
                type = 4,
                listFields = ListFields(
                    title = "hello",
                    summary = "testing something",
                    cover = Icon(
                        url = "https://img2.tapimg.net/post/images/FiIjlxckxO09QV-A2F3cOwE2F2YS.jpeg?imageMogr2/thumbnail/1080x9999%3E/quality/80/format/jpg/interlace/1/strip/meta-keep-list/VXNlckNvbW1lbnQ%3D/ignore-error/1&t=1",
                        mediumUrl = "",
                        smallUrl = "",
                        originalUrl = "",
                        originalFormat = "",
                        width = 300,
                        height = 168,
                        color = "0x8b7c6f"
                    )
                ),
                stat = FeedStat(
                    pvTotal = 9,
                    ups = 1,
                    comments = 3
                ),
                publishedTime = 1716472302
            )
        )
    )

    // UI configuration + Mock Data
    private val _uiState = MutableStateFlow(
        MeUiState(
            myGames = mockMyGames,
            badges = mockBadges,
            posts = mockPosts
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
