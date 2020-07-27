package com.eagskunst.apps.myngs.app.di

import android.content.Context
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheEvictor
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by eagskunst in 27/7/2020.
 */

val exoplayerModule = module {

    single<CacheEvictor> { LeastRecentlyUsedCacheEvictor(200000000L) }

    single(named("ExoPlayerMediaCache")) {
        File(androidContext().cacheDir, "media-cache")
    }

    single { ExoDatabaseProvider(androidContext()) }

    single {
        SimpleCache(
            get<File>(named("ExoPlayerMediaCache")),
            get<CacheEvictor>(),
            get<ExoDatabaseProvider>()
        )
    }

    single { DefaultDataSourceFactory(androidContext(), "Myngs") }

    single <DataSource.Factory> {
        CacheDataSourceFactory(get<SimpleCache>(), get<DefaultDataSourceFactory>())
    }

    factory { (context: Context) -> SimpleExoPlayer.Builder(context).build() }
}
