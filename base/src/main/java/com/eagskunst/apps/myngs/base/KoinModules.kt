package com.eagskunst.apps.myngs.base

import org.koin.core.module.Module

/**
 * Created by eagskunst in 25/7/2020.
 */
interface KoinModules {
    fun all(): List<Module>
    fun allButServicesDbAndBase(): List<Module>
}
