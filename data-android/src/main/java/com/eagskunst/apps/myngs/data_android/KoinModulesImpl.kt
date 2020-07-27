package com.eagskunst.apps.myngs.data_android

import com.eagskunst.apps.myngs.base.KoinModules
import com.eagskunst.apps.myngs.data_android.modules.baseModule
import com.eagskunst.apps.myngs.data_android.modules.daosModule
import com.eagskunst.apps.myngs.data_android.modules.dataSourceModule
import com.eagskunst.apps.myngs.data_android.modules.dbModule
import com.eagskunst.apps.myngs.data_android.modules.interactorsModule
import com.eagskunst.apps.myngs.data_android.modules.networkModule
import com.eagskunst.apps.myngs.data_android.modules.repositoriesModule
import com.eagskunst.apps.myngs.data_android.modules.servicesModule
import com.eagskunst.apps.myngs.data_android.modules.storesModule
import org.koin.core.module.Module

/**
 * Created by eagskunst in 25/7/2020.
 */
object KoinModulesImpl : KoinModules {

    override fun all(): List<Module> {
        return baseModule + dbModule + networkModule + servicesModule + allButServicesDbAndBase()
    }

    override fun allButServicesDbAndBase(): List<Module> {
        return daosModule + dataSourceModule + storesModule +
                dataSourceModule + interactorsModule + repositoriesModule
    }
}
