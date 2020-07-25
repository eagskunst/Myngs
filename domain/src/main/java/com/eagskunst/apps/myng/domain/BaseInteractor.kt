package com.eagskunst.apps.myng.domain

import com.eagskunst.apps.myngs.base.Asyncable
import com.eagskunst.apps.myngs.base.CoroutineDispatchers
import kotlinx.coroutines.withContext

/**
 * Created by eagskunst in 25/7/2020.
 */

abstract class BaseInteractor(protected val dispatchers: CoroutineDispatchers) : Asyncable {

    @JvmName("switchToMainOnly")
    protected suspend inline fun switchToMain(crossinline block: suspend () -> Unit) {
        withContext(`access$dispatchers`.main) { block() }
    }

    @JvmName("switchToMainWithResult")
    protected suspend inline fun <T> switchToMain(crossinline block: suspend () -> T): T {
        return withContext(`access$dispatchers`.main) { block() }
    }

    @JvmName("switchToIoOnly")
    protected suspend inline fun switchToIo(crossinline block: suspend () -> Unit) {
        withContext(`access$dispatchers`.io) { block() }
    }

    @JvmName("switchToIoWithResult")
    protected suspend inline fun <T> switchToIo(crossinline block: suspend () -> T): T {
        return withContext(`access$dispatchers`.io) { block() }
    }

    @JvmName("switchToDefaultOnly")
    protected suspend inline fun switchToDefault(crossinline block: suspend () -> Unit) {
        withContext(`access$dispatchers`.computation) { block() }
    }

    @JvmName("switchToDefaultWithResult")
    protected suspend inline fun <T> switchToDefault(crossinline block: suspend () -> T): T {
        return withContext(this.`access$dispatchers`.computation) { block() }
    }

    @PublishedApi
    internal val `access$dispatchers`: CoroutineDispatchers
        get() = dispatchers

}