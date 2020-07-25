package com.eagskunst.apps.myngs.data_android.modules

import com.eagskunst.apps.myngs.data.repositories.album.AlbumDataStore
import com.eagskunst.apps.myngs.data.repositories.searchterm.SearchDataStore
import com.eagskunst.apps.myngs.data.repositories.songs.SongsDataStore
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */

val storesModule = module {
    factory { AlbumDataStore(get(), get()) }
    factory { SongsDataStore(get()) }
    factory { SearchDataStore(get()) }
}