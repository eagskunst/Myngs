package com.eagskunst.apps.myngs.base

/**
 * Created by eagskunst in 25/7/2020.
 * From Chris Banes's Tivi:
 * https://github.com/chrisbanes/tivi/blob/main/base/src/main/java/app/tivi/data/entities/Result.kt
 */
sealed class DataResult<T> {

    open fun get(): T? = null

    fun getOrThrow(): T = when (this) {
        is Success -> get()
        is ErrorResult -> throw throwable
    }

}

data class Success<T>(val data: T) : DataResult<T>() {
    override fun get(): T = data
}

data class ErrorResult<T>(val throwable: Throwable) : DataResult<T>()