package com.eagskunst.apps.myngs.data_android.modules

import com.eagskunst.apps.myngs.data.MyngsDb
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */

val daosModule = module {
    factory { get<MyngsDb>().albumDao() }
    factory { get<MyngsDb>().searchDao() }
    factory { get<MyngsDb>().songDao() }
}