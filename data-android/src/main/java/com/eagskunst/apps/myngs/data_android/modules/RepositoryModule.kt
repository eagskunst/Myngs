package com.eagskunst.apps.myngs.data_android.modules

import com.eagskunst.apps.myngs.data.repositories.album.AlbumRepository
import com.eagskunst.apps.myngs.data.repositories.searchterm.SearchTermRepository
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */

val repositoriesModule = module {
    single { AlbumRepository(get(), get(), get()) }
    single { SearchTermRepository(get(), get(), get()) }
}