package com.eagskunst.apps.myngs.base

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Created by eagskunst in 25/7/2020.
 */
interface Asyncable {

    suspend fun <T> runSafely(block: () -> T): DataResult<T> {
        return try {
            val res = block()
            Success(res)
        } catch (e: Exception) {
            ErrorResult(e.cause!!)
        }
    }

    suspend fun <T> runAndForget(block: () -> T) = coroutineScope {
        launch { runSafely(block) }
    }

    suspend fun <T> runDeferred(block: () -> T) = coroutineScope {
        async { runSafely(block) }
    }

}