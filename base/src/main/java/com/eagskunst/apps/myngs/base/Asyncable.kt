package com.eagskunst.apps.myngs.base

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

}