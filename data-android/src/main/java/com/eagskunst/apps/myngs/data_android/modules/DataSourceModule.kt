package com.eagskunst.apps.myngs.data_android.modules

import com.eagskunst.apps.myngs.data.mapper.SongToAlbumMapper
import com.eagskunst.apps.myngs.data.mapper.TunesQueryToAlbumWithSongsMapper
import com.eagskunst.apps.myngs.data.mapper.TunesQueryToSongsMapper
import com.eagskunst.apps.myngs.data.repositories.album.AlbumDataSource
import com.eagskunst.apps.myngs.data.repositories.searchterm.SearchDataSource
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */

val dataSourceModule = module {
    factory { TunesQueryToSongsMapper() }
    factory { SongToAlbumMapper() }
    factory { TunesQueryToAlbumWithSongsMapper(get()) }
    factory { AlbumDataSource(get(), get()) }
    factory { SearchDataSource(get(), get()) }
}