package com.compose.taptap.core.designsystem.component.atoms.video

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheWriter
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
object VideoCacheManager {
    private var simpleCache: SimpleCache? = null
    
    // Max cache size: 100MB
    private const val MAX_CACHE_SIZE: Long = 100 * 1024 * 1024 

    fun initialize(context: Context) {
        if (simpleCache == null) {
            val cacheDir = File(context.cacheDir, "taptap_video_cache")
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            
            val dbProvider = StandaloneDatabaseProvider(context)
            val evictor = LeastRecentlyUsedCacheEvictor(MAX_CACHE_SIZE)
            
            simpleCache = SimpleCache(cacheDir, evictor, dbProvider)
        }
    }

    fun getDataSourceFactory(context: Context): DataSource.Factory {
        if (simpleCache == null) {
            initialize(context)
        }

        val upstreamFactory = DefaultDataSource.Factory(context)
        
        return CacheDataSource.Factory()
            .setCache(simpleCache!!)
            .setUpstreamDataSourceFactory(upstreamFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }
    
    fun release() {
        simpleCache?.release()
        simpleCache = null
    }

    /**
     * Preloads a video to the cache to enable instant playback.
     * Should be called from a background thread or coroutine.
     */
    private val scope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    /**
     * Preloads a video to the cache to enable instant playback.
     * Should be called from a background thread or coroutine.
     */
    fun preloadVideo(context: Context, uri: Uri) {
        if (simpleCache == null) {
            initialize(context)
        }
        
        // Launch in internal scope for fire-and-forget preloading
        scope.launch {
            try {
                val dataSpec = DataSpec(uri)
                val upstreamFactory = DefaultDataSource.Factory(context)
                val cacheDataSource = CacheDataSource(
                    simpleCache!!,
                    upstreamFactory.createDataSource(),
                    CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR
                )
                
                val cacheWriter = CacheWriter(
                    cacheDataSource,
                    dataSpec,
                    null, // temporaryBuffer
                    null // progressListener
                )
                
                cacheWriter.cache()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
