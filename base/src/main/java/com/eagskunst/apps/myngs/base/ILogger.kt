package com.eagskunst.apps.myngs.base

import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by eagskunst in 25/7/2020.
 */
interface ILogger {
    fun d(message: String)
    fun e(message: String)
    fun e(throwable: Throwable, message: String)
    fun i(message: String)
}

object Timber: ILogger, KoinComponent {

    private val logger: ILogger by inject()

    override fun d(message: String) = logger.d(message)
    override fun e(message: String) = logger.e(message)
    override fun e(throwable: Throwable, message: String) = logger.e(throwable, message)
    override fun i(message: String) = logger.i(message)
}