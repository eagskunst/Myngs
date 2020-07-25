package com.eagskunst.apps.myngs.tests.robots

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * Created by eagskunst in 25/7/2020.
 */
class ConditionRobot {
    inline fun waitUntil(
        waitMillisPerTry: Long = 100L,
        maxTries: Int = 10,
        conditionFunction: () -> Boolean
    ) {
        var tries = 0
        for (i in 0..tries) {
            try {
                tries++
                val result = conditionFunction()
                if (!result)
                    throw FalseConditionException("The condition of ${Thread.currentThread().name} has not been met")
            } catch (e: Exception) {
                if (tries >= maxTries) {
                    throw e
                }
                runBlocking { delay(waitMillisPerTry) }
            }
        }
    }

    inner class FalseConditionException(msg: String) : Exception(msg)
}