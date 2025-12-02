package com.compose.taptap.core.preview

import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.model.App
import com.compose.taptap.core.model.Icon
import com.compose.taptap.core.model.User

object PreviewUtils {
    val mockUser = User(
        id = 12123123,
        name = "TapTap Editor",
        avatar = "",
        mediumAvatar = "",
        gender = "",
        store = "",
        intro = "",
        isCertified = true,
        isAnonymous = true,
        isBan = false,
        isDeactivated = false,
    )

    val mockGames = listOf(
        App(
            id = 135082,
            identifier = "com.supercell.brawlstars",
            title = "Brawl Stars",
            titleLabels = listOf("Global"),
            icon = Icon(
                url = R.drawable.brawl_stars_app_icon.toString(),
                mediumUrl = R.drawable.brawl_stars_app_icon.toString(),
                smallUrl = R.drawable.brawl_stars_app_icon.toString(),
                originalUrl = "",
                originalFormat = "",
                width = 0,
                height = 0,
                color = ""
            ),
            uri = null,
            canView = true,
            releasedTime = 1530072066,
            stat = null,
            banner = null,
            tags = null,
            log = null,
            eventLog = null,
            complaint = null,
            supportedPlatforms = null,
            itunesId = "1229016807",
            recText = "Brawliday Opening, new game modes and skins await you!",
            videoResource = null
        ), App(
            id = 214547,
            identifier = "com.nebulajoy.act.dmcpoc",
            title = "Devil May Cry: Peak of Combat",
            titleLabels = listOf("Global"),
            icon = Icon(
                url = R.drawable.devil_may_cry_app_icon.toString(),
                mediumUrl = R.drawable.devil_may_cry_app_icon.toString(),
                smallUrl = R.drawable.devil_may_cry_app_icon.toString(),
                originalUrl = "",
                originalFormat = "",
                width = 0,
                height = 0,
                color = ""
            ),
            uri = null,
            canView = true,
            releasedTime = 1619766431,
            stat = null,
            banner = null,
            tags = null,
            log = null,
            eventLog = null,
            complaint = null,
            supportedPlatforms = null,
            itunesId = "6449589065",
            recText = "Only DMC Licensed on Mobile",
            videoResource = null
        ), App(
            id = 178546,
            identifier = "air.com.ubisoft.brawl.halla.platform.fighting.action.pvp",
            title = "Brawlhalla",
            titleLabels = emptyList(),
            icon = Icon(
                url = R.drawable.brawl_halla_app_icon.toString(),
                mediumUrl = R.drawable.brawl_halla_app_icon.toString(),
                smallUrl = R.drawable.brawl_halla_app_icon.toString(),
                originalUrl = "",
                originalFormat = "",
                width = 0,
                height = 0,
                color = ""
            ),
            uri = null,
            canView = true,
            releasedTime = 1596165246,
            stat = null,
            banner = null,
            tags = null,
            log = null,
            eventLog = null,
            complaint = null,
            supportedPlatforms = null,
            itunesId = "1491520571",
            recText = "Friends from Kung Fu Panda have joined Brawl!",
            videoResource = null
        )
    )
}
