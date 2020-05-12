package com.mackosoft.testtrax.utils

import android.content.Context
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

private lateinit var sharedCache: SimpleCache


// cache management from https://stackoverflow.com/a/52948569/3535408
fun getCache(context: Context): SimpleCache {
    if (::sharedCache.isInitialized.not()) {
        val cacheDir = File(context.cacheDir, "events_video")
        val cacheEvictor = LeastRecentlyUsedCacheEvictor(50L * 1024L * 1024L) // 50 Mo
        sharedCache = SimpleCache(cacheDir, cacheEvictor, ExoDatabaseProvider(context))
    }

    return sharedCache
}

