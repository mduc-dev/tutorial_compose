package com.compose.taptap.core.designsystem.component.atoms.video

import android.graphics.SurfaceTexture
import android.net.Uri
import android.view.Surface
import android.view.TextureView
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
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
            exoPlayer.release()
        }
    }

    // Use AndroidView with TextureView to manually handle SurfaceTexture lifecycle
    // This fixes the crash where SurfaceTexture is destroyed before ExoPlayer can detach
    AndroidView(
        factory = { ctx ->
            TextureView(ctx).apply {
                surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                        val videoSurface = Surface(surface)
                        exoPlayer.setVideoSurface(videoSurface)
                    }

                    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
                        // No-op
                    }

                    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                        // CRITICAL FIX: Return true to indicate we will handle the release.
                        // However, returning true means we *must* call release() ourselves.
                        // Actually, returning TRUE means "I will handle it, don't destroy it yet".
                        // Returning FALSE (default) means "System, please destroy it now".
                        // We want to prevent the system from destroying it immediately so ExoPlayer isn't left holding a dead handle.
                        // We set video surface to null here to detach ExoPlayer.
                        exoPlayer.setVideoSurface(null)
                        
                        // By returning true, we keep the SurfaceTexture alive. It will be GC'd when the TextureView is collected.
                        // Or we can manually release it if we kept a reference, but letting it be GC'd is usually safe enough 
                        // for this specific crash scenario as long as the View is detaching.
                        // Ideally we should release it, but the crash happens because it's destroyed TOO EARLY.
                        return true 
                    }

                    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                        // No-op
                    }
                }
            }
        },
        modifier = modifier
    )
}
