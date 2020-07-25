package com.eagskunst.apps.myng.domain

import com.eagskunst.apps.myngs.base.Asyncable
import com.eagskunst.apps.myngs.base.CoroutineDispatchers
import kotlinx.coroutines.withContext

/**
 * Created by eagskunst in 25/7/2020.
 */

abstract class BaseInteractor : Asyncable {
    abstract val dispatchers: CoroutineDispatchers

    @JvmName("switchToMainOnly")
    suspend inline fun switchToMain(crossinline block: () -> Unit) {
        withContext(dispatchers.main) { block() }
    }

    @JvmName("switchToMainWithResult")
    suspend inline fun <T> switchToMain(crossinline block: () -> T): T {
        return withContext(dispatchers.main) { block() }
    }

    @JvmName("switchToIoOnly")
    suspend inline fun switchToIo(crossinline block: () -> Unit) {
        withContext(dispatchers.io) { block() }
    }

    @JvmName("switchToIoWithResult")
    suspend inline fun <T> switchToIo(crossinline block: () -> T): T {
        return withContext(dispatchers.io) { block() }
    }

    @JvmName("switchToDefaultOnly")
    suspend inline fun switchToDefault(crossinline block: () -> Unit) {
        withContext(dispatchers.computation) { block() }
    }

    @JvmName("switchToDefaultWithResult")
    suspend inline fun <T> switchToDefault(crossinline block: () -> T): T {
        return withContext(dispatchers.computation) { block() }
    }

}