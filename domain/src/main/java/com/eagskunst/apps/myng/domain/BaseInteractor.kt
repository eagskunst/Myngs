package com.eagskunst.apps.myng.domain

import com.eagskunst.apps.myngs.base.*
import com.eagskunst.apps.myngs.base.errors.EmptySearchException
import jdk.nashorn.internal.parser.JSONParser
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by eagskunst in 25/7/2020.
 */

abstract class BaseInteractor(protected val dispatchers: CoroutineDispatchers) : Asyncable {

    protected suspend inline fun switchToMain(crossinline block: suspend () -> Unit) {
        withContext(`access$dispatchers`.main) { block() }
    }

    protected suspend inline fun <T> switchToMainWithResult(crossinline block: suspend () -> T): T {
        return withContext(`access$dispatchers`.main) { block() }
    }

    protected suspend inline fun switchToIo(crossinline block: suspend () -> Unit) {
        withContext(`access$dispatchers`.io) { block() }
    }

    protected suspend inline fun <T> switchToIoWithResult(crossinline block: suspend () -> T): T {
        return withContext(`access$dispatchers`.io) { block() }
    }

    protected suspend inline fun switchToDefault(crossinline block: suspend () -> Unit) {
        withContext(`access$dispatchers`.computation) { block() }
    }

    protected suspend inline fun <T> switchToDefaultWithResult(crossinline block: suspend () -> T): T {
        return withContext(this.`access$dispatchers`.computation) { block() }
    }

    @PublishedApi
    internal val `access$dispatchers`: CoroutineDispatchers
        get() = dispatchers

    protected fun <T> addErrorInformationToResult(errorResult: ErrorResult<T>): DataResult<T> {
        val errorInfo = errorResult.errorInfo
        val newInfo = when(errorInfo.throwable) {
            is HttpException -> errorInfo.copy(message = ErrorMessage.Unknown) //TODO parse json message
            is IOException -> errorInfo.copy(message = ErrorMessage.Connection)
            is SocketTimeoutException -> errorInfo.copy(message = ErrorMessage.Timeout)
            is EmptySearchException -> errorInfo.copy(message = ErrorMessage.TermNotFound)
            else -> errorInfo.copy(message = ErrorMessage.Unknown)
        }

        return ErrorResult(errorResult.throwable, newInfo)
    }


    companion object {
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

}