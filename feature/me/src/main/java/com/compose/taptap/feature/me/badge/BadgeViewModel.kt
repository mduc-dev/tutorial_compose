package com.compose.taptap.feature.me.badge

import androidx.lifecycle.viewModelScope
import com.compose.taptap.core.designsystem.util.loading
import com.compose.taptap.core.designsystem.util.loadingSuccess
import com.compose.taptap.core.model.BadgeItem
import com.compose.taptap.core.model.Icon
import com.compose.taptap.core.viewmodel.BaseViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class BadgeViewModel : BaseViewModel() {

    private val mockBadges = persistentListOf(
        BadgeItem(
            id = 4,
            title = "Indie Pioneer",
            description = "Venture into the unknown, and champion the indie spirit.",
            earnLink = "tapglobal://taptap.tw/to?url=https%3A%2F%2Fwww.taptap.io%2Fevents%2Fplayfest2023winter",
            congratulateDescription = "Congrats! You've been awarded the Indie Pioneer badge for clearing all quests in this indie celebration event. Keep your indie soul and celebrate your journey into the heart of the indie world!",
            smallImage = Icon(
                url = "https://img.tapimg.net/market/images/fd824c26d7702ae580f49588cd62986b.png",
                mediumUrl = "https://img.tapimg.net/market/images/fd824c26d7702ae580f49588cd62986b.png",
                smallUrl = "https://img.tapimg.net/market/images/fd824c26d7702ae580f49588cd62986b.png",
                originalUrl = "https://img.tapimg.net/market/images/fd824c26d7702ae580f49588cd62986b.png",
                originalFormat = "png",
                width = 36,
                height = 42,
                color = "0x6f6a73",
                originalSize = 2804
            ),
            middleImage = Icon(
                url = "https://img.tapimg.net/market/images/6d52682e768ca0328d2ee3ef5ec23ac0.png",
                mediumUrl = "https://img.tapimg.net/market/images/6d52682e768ca0328d2ee3ef5ec23ac0.png",
                smallUrl = "https://img.tapimg.net/market/images/6d52682e768ca0328d2ee3ef5ec23ac0.png",
                originalUrl = "https://img.tapimg.net/market/images/6d52682e768ca0328d2ee3ef5ec23ac0.png",
                originalFormat = "png",
                width = 240,
                height = 240,
                color = "0x7a7575",
                originalSize = 54031
            ),
            largeImage = Icon(
                url = "https://img.tapimg.net/market/images/c7f725a498198e9576a145b23f5d4c8d.png",
                mediumUrl = "https://img.tapimg.net/market/images/c7f725a498198e9576a145b23f5d4c8d.png",
                smallUrl = "https://img.tapimg.net/market/images/c7f725a498198e9576a145b23f5d4c8d.png",
                originalUrl = "https://img.tapimg.net/market/images/c7f725a498198e9576a145b23f5d4c8d.png",
                originalFormat = "png",
                width = 720,
                height = 720,
                color = "0x7c7777",
                originalSize = 385851
            ),
            isEarned = false,
            isWear = false,
            canEarn = true,
            earnedCount = 0
        ),
        BadgeItem(
            id = 2,
            title = "Seal of Quality",
            description = "Now that’s quality. This badge is awarded for posting content at a caliber above the rest.",
            earnLink = "tapglobal://taptap.tw/creation/post?post_id=6250815",
            congratulateDescription = "Congrats. You've been awarded the Seal of Quality badge for posting S tier level content. You're now invited to join a secret channel within our Discord for exclusive events. Be sure to say hi ;)",
            congratulateButtonText = "Grab the next",
            congratulateLink = "https://discord.gg/Wk5MNhX73V",
            smallImage = Icon(
                url = "https://img.tapimg.net/market/images/e44498c251eae12416e7bb4c2f66893d.png",
                mediumUrl = "https://img.tapimg.net/market/images/e44498c251eae12416e7bb4c2f66893d.png",
                smallUrl = "https://img.tapimg.net/market/images/e44498c251eae12416e7bb4c2f66893d.png",
                originalUrl = "https://img.tapimg.net/market/images/e44498c251eae12416e7bb4c2f66893d.png",
                originalFormat = "png",
                width = 42,
                height = 42,
                color = "0x99554d",
                originalSize = 2661
            ),
            middleImage = Icon(
                url = "https://img.tapimg.net/market/images/6f9fb6a88488de8e057c88babbca1d19.png",
                mediumUrl = "https://img.tapimg.net/market/images/6f9fb6a88488de8e057c88babbca1d19.png",
                smallUrl = "https://img.tapimg.net/market/images/6f9fb6a88488de8e057c88babbca1d19.png",
                originalUrl = "https://img.tapimg.net/market/images/6f9fb6a88488de8e057c88babbca1d19.png",
                originalFormat = "png",
                width = 160,
                height = 160,
                color = "0x995f58",
                originalSize = 32523
            ),
            largeImage = Icon(
                url = "https://img.tapimg.net/market/images/d6567698f7daa294a796b5d6416f6510.png",
                mediumUrl = "https://img.tapimg.net/market/images/d6567698f7daa294a796b5d6416f6510.png",
                smallUrl = "https://img.tapimg.net/market/images/d6567698f7daa294a796b5d6416f6510.png",
                originalUrl = "https://img.tapimg.net/market/images/d6567698f7daa294a796b5d6416f6510.png",
                originalFormat = "png",
                width = 720,
                height = 720,
                color = "0x995c56",
                originalSize = 471179
            ),
            isEarned = false,
            isWear = false,
            canEarn = true,
            earnedCount = 0
        ),
        BadgeItem(
            id = 3,
            title = "Community Insider",
            description = "What comes after a Seal of Quality? Welcome to our exclusive private Discord channel.",
            earnLink = "tapglobal://taptap.tw/creation/post?post_id=6250815",
            congratulateDescription = "Congrats. You've been awarded the Community Insider badge and welcome to our exclusive Discord channel. Hear exclusive gaming news before anyone else and be amongst your fellow Community Insiders.",
            smallImage = Icon(
                url = "https://img.tapimg.net/market/images/60458df111215a9814150431baec0a4c.png",
                mediumUrl = "https://img.tapimg.net/market/images/60458df111215a9814150431baec0a4c.png",
                smallUrl = "https://img.tapimg.net/market/images/60458df111215a9814150431baec0a4c.png",
                originalUrl = "https://img.tapimg.net/market/images/60458df111215a9814150431baec0a4c.png",
                originalFormat = "png",
                width = 42,
                height = 42,
                color = "0x837099",
                originalSize = 2818
            ),
            middleImage = Icon(
                url = "https://img.tapimg.net/market/images/2e724cdefbd570e9ec8aade7c3c2c6d7.png",
                mediumUrl = "https://img.tapimg.net/market/images/2e724cdefbd570e9ec8aade7c3c2c6d7.png",
                smallUrl = "https://img.tapimg.net/market/images/2e724cdefbd570e9ec8aade7c3c2c6d7.png",
                originalUrl = "https://img.tapimg.net/market/images/2e724cdefbd570e9ec8aade7c3c2c6d7.png",
                originalFormat = "png",
                width = 160,
                height = 160,
                color = "0x8b7386",
                originalSize = 30731
            ),
            largeImage = Icon(
                url = "https://img.tapimg.net/market/images/640c50db0db0c7cf1ef267e0a4f424c1.png",
                mediumUrl = "https://img.tapimg.net/market/images/640c50db0db0c7cf1ef267e0a4f424c1.png",
                smallUrl = "https://img.tapimg.net/market/images/640c50db0db0c7cf1ef267e0a4f424c1.png",
                originalUrl = "https://img.tapimg.net/market/images/640c50db0db0c7cf1ef267e0a4f424c1.png",
                originalFormat = "png",
                width = 720,
                height = 720,
                color = "0x90788f",
                originalSize = 386710
            ),
            isEarned = false,
            isWear = false,
            canEarn = true,
            earnedCount = 0
        ),
        BadgeItem(
            id = 6,
            title = "Explorer's Heart",
            description = "This badge honors the explorers for the TapTap Discord's season 1 event.",
            congratulateDescription = "You embarked on a journey of discovery to uncover the secrets of new games. This badge honors your participation in our Discord's season 1 event.",
            smallImage = Icon(
                url = "https://img.tapimg.net/market/images/9c5d428d86995a0fef12a115b17d19e0.png",
                mediumUrl = "https://img.tapimg.net/market/images/9c5d428d86995a0fef12a115b17d19e0.png",
                smallUrl = "https://img.tapimg.net/market/images/9c5d428d86995a0fef12a115b17d19e0.png",
                originalUrl = "https://img.tapimg.net/market/images/9c5d428d86995a0fef12a115b17d19e0.png",
                originalFormat = "png",
                width = 12,
                height = 14,
                color = "0x73504e",
                originalSize = 647
            ),
            middleImage = Icon(
                url = "https://img.tapimg.net/market/images/c1167b9d6d6d4052b9c52263fd76addc.png",
                mediumUrl = "https://img.tapimg.net/market/images/c1167b9d6d6d4052b9c52263fd76addc.png",
                smallUrl = "https://img.tapimg.net/market/images/c1167b9d6d6d4052b9c52263fd76addc.png",
                originalUrl = "https://img.tapimg.net/market/images/c1167b9d6d6d4052b9c52263fd76addc.png",
                originalFormat = "png",
                width = 80,
                height = 80,
                color = "0x7e6562",
                originalSize = 10003
            ),
            largeImage = Icon(
                url = "https://img.tapimg.net/market/images/4b47ca589381b8a8835978af672e42e0.png",
                mediumUrl = "https://img.tapimg.net/market/images/4b47ca589381b8a8835978af672e42e0.png",
                smallUrl = "https://img.tapimg.net/market/images/4b47ca589381b8a8835978af672e42e0.png",
                originalUrl = "https://img.tapimg.net/market/images/4b47ca589381b8a8835978af672e42e0.png",
                originalFormat = "png",
                width = 720,
                height = 720,
                color = "0x816562",
                originalSize = 444941
            ),
            isEarned = false,
            isWear = false,
            canEarn = true,
            earnedCount = 0
        ),
        BadgeItem(
            id = 7,
            title = "We are Game Gang",
            description = "Unlock the TapTap Discord Newbie Badge by participating in our Discord community for first 7 days.",
            earnLink = "https://discord.gg/taptap",
            congratulateDescription = "Congrats. You've been awarded the TapTap Discord Newbie Badge - We are Game Gang. Continue to stay updated with latest TapTap news in the Discord Community",
            smallImage = Icon(
                url = "https://img.tapimg.net/market/images/59bf068c6d174c7fdf5b16e840e23686.png",
                mediumUrl = "https://img.tapimg.net/market/images/59bf068c6d174c7fdf5b16e840e23686.png",
                smallUrl = "https://img.tapimg.net/market/images/59bf068c6d174c7fdf5b16e840e23686.png",
                originalUrl = "https://img.tapimg.net/market/images/59bf068c6d174c7fdf5b16e840e23686.png",
                originalFormat = "png",
                width = 27,
                height = 28,
                color = "0x695199",
                originalSize = 1450
            ),
            middleImage = Icon(
                url = "https://img.tapimg.net/market/images/0b74f3ae0fc6792db81b161e33be970b.png",
                mediumUrl = "https://img.tapimg.net/market/images/0b74f3ae0fc6792db81b161e33be970b.png",
                smallUrl = "https://img.tapimg.net/market/images/0b74f3ae0fc6792db81b161e33be970b.png",
                originalUrl = "https://img.tapimg.net/market/images/0b74f3ae0fc6792db81b161e33be970b.png",
                originalFormat = "png",
                width = 160,
                height = 160,
                color = "0x7a6989",
                originalSize = 34202
            ),
            largeImage = Icon(
                url = "https://img.tapimg.net/market/images/b54ed11dbd29185aede2f56200acda70.png",
                mediumUrl = "https://img.tapimg.net/market/images/b54ed11dbd29185aede2f56200acda70.png",
                smallUrl = "https://img.tapimg.net/market/images/b54ed11dbd29185aede2f56200acda70.png",
                originalUrl = "https://img.tapimg.net/market/images/b54ed11dbd29185aede2f56200acda70.png",
                originalFormat = "png",
                width = 480,
                height = 480,
                color = "0x7b6a8f",
                originalSize = 227131
            ),
            isEarned = false,
            isWear = false,
            canEarn = true,
            earnedCount = 0
        ),
        BadgeItem(
            id = 1,
            title = "Top Shelf",
            description = "Awarded for featured posts during an event selected by the TapTap editorial team.",
            earnLink = "tapglobal://taptap.tw/creation/post?post_id=6250815",
            congratulateDescription = "Congrats. You've been awarded the Top Shelf badge by having your engaging post selected to be featured by the TapTap editorial team.",
            smallImage = Icon(
                url = "https://img.tapimg.net/market/images/4c545486746a1d28e35201b4e571f9fd.png",
                mediumUrl = "https://img.tapimg.net/market/images/4c545486746a1d28e35201b4e571f9fd.png",
                smallUrl = "https://img.tapimg.net/market/images/4c545486746a1d28e35201b4e571f9fd.png",
                originalUrl = "https://img.tapimg.net/market/images/4c545486746a1d28e35201b4e571f9fd.png",
                originalFormat = "png",
                width = 42,
                height = 42,
                color = "0x99734e",
                originalSize = 2835
            ),
            middleImage = Icon(
                url = "https://img.tapimg.net/market/images/72cb326e990cf8cd5fa3dd56e5a0a9df.png",
                mediumUrl = "https://img.tapimg.net/market/images/72cb326e990cf8cd5fa3dd56e5a0a9df.png",
                smallUrl = "https://img.tapimg.net/market/images/72cb326e990cf8cd5fa3dd56e5a0a9df.png",
                originalUrl = "https://img.tapimg.net/market/images/72cb326e990cf8cd5fa3dd56e5a0a9df.png",
                originalFormat = "png",
                width = 160,
                height = 160,
                color = "0x99744b",
                originalSize = 38236
            ),
            largeImage = Icon(
                url = "https://img.tapimg.net/market/images/29221a52aa8352201978bb8e6ffc2c5c.png",
                mediumUrl = "https://img.tapimg.net/market/images/29221a52aa8352201978bb8e6ffc2c5c.png",
                smallUrl = "https://img.tapimg.net/market/images/29221a52aa8352201978bb8e6ffc2c5c.png",
                originalUrl = "https://img.tapimg.net/market/images/29221a52aa8352201978bb8e6ffc2c5c.png",
                originalFormat = "png",
                width = 720,
                height = 720,
                color = "0x997348",
                originalSize = 512160
            ),
            isEarned = false,
            isWear = false,
            canEarn = true,
            earnedCount = 0
        ),
        BadgeItem(
            id = 5,
            title = "The First Adventure",
            description = "This badge honors the first adventurers for TapTap Tavern's Season 0.",
            congratulateDescription = "Congrats tavern adventurer, you've been awarded the First Adventure badge. Never give up that discovering spirit.",
            smallImage = Icon(
                url = "https://img.tapimg.net/market/images/2b19d0f7be6cef4d84f6ef23966fe837.png",
                mediumUrl = "https://img.tapimg.net/market/images/2b19d0f7be6cef4d84f6ef23966fe837.png",
                smallUrl = "https://img.tapimg.net/market/images/2b19d0f7be6cef4d84f6ef23966fe837.png",
                originalUrl = "https://img.tapimg.net/market/images/2b19d0f7be6cef4d84f6ef23966fe837.png",
                originalFormat = "png",
                width = 56,
                height = 56,
                color = "0x737d6f",
                originalSize = 4463
            ),
            middleImage = Icon(
                url = "https://img.tapimg.net/market/images/3b4a46ee7467a7ff3742a5c6370b2439.png",
                mediumUrl = "https://img.tapimg.net/market/images/3b4a46ee7467a7ff3742a5c6370b2439.png",
                smallUrl = "https://img.tapimg.net/market/images/3b4a46ee7467a7ff3742a5c6370b2439.png",
                originalUrl = "https://img.tapimg.net/market/images/3b4a46ee7467a7ff3742a5c6370b2439.png",
                originalFormat = "png",
                width = 320,
                height = 320,
                color = "0x84826c",
                originalSize = 94298
            ),
            largeImage = Icon(
                url = "https://img.tapimg.net/market/images/f90d06b68157c8b0a9917cd333d4d69a.png",
                mediumUrl = "https://img.tapimg.net/market/images/f90d06b68157c8b0a9917cd333d4d69a.png",
                smallUrl = "https://img.tapimg.net/market/images/f90d06b68157c8b0a9917cd333d4d69a.png",
                originalUrl = "https://img.tapimg.net/market/images/f90d06b68157c8b0a9917cd333d4d69a.png",
                originalFormat = "png",
                width = 797,
                height = 797,
                color = "0x86846d",
                originalSize = 383894
            ),
            isEarned = false,
            isWear = false,
            canEarn = true,
            earnedCount = 0
        )
    )

    val badgeState = flowOf(loadingSuccess(mockBadges))
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = loading()
        )

    val selectedBadge = MutableStateFlow(mockBadges.firstOrNull { it.id == 4L }) // Default to Indie Pioneer or null
        .asStateFlow()
}
