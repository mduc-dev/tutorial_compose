package com.compose.taptap.core.designsystem.component.molecules.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarIconSize
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.model.UserProfileData

@Composable
fun MeTabIcon(
    selected: Boolean,
    userProfile: UserProfileData?
) {
    Box(
        modifier = Modifier.size(width = 50.dp, height = 44.dp),
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(BottomBarIconSize)
        ) {
            if (userProfile?.avatar != null) {
                AsyncImage(
                    model = userProfile.avatar,
                    contentDescription = "user-avatar",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(1.dp, TapTapTheme.colors.surface, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.intl_cc_24_general_user_default),
                    contentDescription = "user-avatar-default",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

        Text(
            text = "You",
            color = if (selected) TapTapTheme.colors.onBackground else Color.Gray,
            style = TapTapTheme.typography.labelMedium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 1.dp)
        )
    }
}
