package com.eagskunst.apps.myngs.base_android

import com.eagskunst.apps.myngs.base.MyngsLogger
import timber.log.Timber

/**
 * Created by eagskunst in 25/7/2020.
 */
class TimberLogger: MyngsLogger {
    override fun d(message: String) = Timber.d(message) // this is real timber this time
    override fun e(message: String) = Timber.e(message)
    override fun e(throwable: Throwable, message: String) = Timber.e(throwable, message)
    override fun i(message: String) = Timber.i(message)

    companion object {
        fun init() {
            Timber.plant(Timber.DebugTree())
        }
    }
}