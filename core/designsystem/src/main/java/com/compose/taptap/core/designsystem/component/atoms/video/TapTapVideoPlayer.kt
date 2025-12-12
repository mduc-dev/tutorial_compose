package com.compose.taptap.core.designsystem.component.atoms.video

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.AndroidEmbeddedExternalSurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.compose.runtime.Immutable
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory

@Immutable
data class StableUri(val value: Uri)

@OptIn(UnstableApi::class)
@Composable
fun TapTapVideoPlayer(
    uri: StableUri,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    repeatMode: Int = Player.REPEAT_MODE_ONE,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val videoUri = uri.value

    val exoPlayer = remember(videoUri) {
        val dataSourceFactory = VideoCacheManager.getDataSourceFactory(context)
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(dataSourceFactory))
            .build()
            .apply {
                val mediaItem = MediaItem.fromUri(videoUri)
                setMediaItem(mediaItem)
                this.repeatMode = repeatMode
                prepare()
                if (autoPlay) {
                    playWhenReady = true
                }
            }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (autoPlay) exoPlayer.play()
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                exoPlayer.pause()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.stop()
            exoPlayer.clearVideoSurface()
            exoPlayer.release()
        }
    }

    // Use AndroidEmbeddedExternalSurface (Pure Compose TextureView wrapper)
    // This avoids explicit AndroidView usage while providing texture-based rendering to prevent ghosting.
    AndroidEmbeddedExternalSurface(
        modifier = modifier,
        onInit = {
            onSurface { surface, _, _ ->
                exoPlayer.setVideoSurface(surface)
            }
        }
    )
}
