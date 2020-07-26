package com.eagskunst.apps.myngs.app.di

import com.eagskunst.apps.myngs.base.MyngsLogger
import com.eagskunst.apps.myngs.base_android.TimberLogger
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */

val appModule = module {
    single<MyngsLogger> {
        TimberLogger()
    }
}