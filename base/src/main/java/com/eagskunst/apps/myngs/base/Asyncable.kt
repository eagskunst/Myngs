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
            ErrorResult(e.cause!!)
        }
    }

    suspend fun <T> runAndForget(block: suspend () -> T) = coroutineScope {
        launch { runSafely(block) }
    }

    suspend fun <T> runDeferred(block: suspend () -> T) = coroutineScope {
        async { runSafely(block) }
    }

}