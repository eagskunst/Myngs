package com.eagskunst.apps.myngs.data_android.modules

import com.eagskunst.apps.myng.domain.interactors.GetAlbum
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */

val interactorsModule = module {
    single { SearchTerm(get(), get()) }
    single { GetAlbum(get(), get()) }
}
