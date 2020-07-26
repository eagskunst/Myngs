package com.eagskunst.apps.myngs.base

/**
 * Created by eagskunst in 25/7/2020.
 */
open class ColdEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set


    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content

}