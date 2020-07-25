package com.eagskunst.apps.myngs.data_android.modules

import com.eagskunst.apps.myngs.base.CoroutineDispatchers
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */

val baseModule = module {
    factory { CoroutineDispatchers() }
}