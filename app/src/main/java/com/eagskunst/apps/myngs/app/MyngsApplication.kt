package com.eagskunst.apps.myngs.app

import android.app.Application
import com.eagskunst.apps.myngs.app.di.appModule
import com.eagskunst.apps.myngs.app.di.exoplayerModule
import com.eagskunst.apps.myngs.app.di.viewModelModule
import com.eagskunst.apps.myngs.base_android.TimberLogger
import com.eagskunst.apps.myngs.data_android.KoinModulesImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by eagskunst in 25/7/2020.
 */

class MyngsApplication : Application() {

    companion object {
        lateinit var instance: MyngsApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        TimberLogger.init()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyngsApplication)
            modules(appModule + viewModelModule + exoplayerModule + KoinModulesImpl.all())
        }
    }

}