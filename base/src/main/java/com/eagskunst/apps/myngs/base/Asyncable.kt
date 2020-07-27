package com.eagskunst.apps.myngs.base

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Created by eagskunst in 25/7/2020.
 */
interface Asyncable {

    suspend fun <T> runSafely(block: suspend () -> T): DataResult<T> {
        return try {
            val res = block()
            Success(res)
        } catch (e: Exception) {
            ErrorResult(e.cause ?: Exception(""))
        }
    }

    suspend fun runAndForget(block: suspend () -> Unit) = coroutineScope {
        launch { block() }
    }

    suspend fun runDeferred(block: suspend () -> Unit) = coroutineScope {
        async { block }
    }
}
