package com.eagskunst.apps.myngs.tests

import com.eagskunst.apps.myngs.base.CoroutineDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */
@ExperimentalCoroutinesApi
class DispatchersModule {

    private val testDispatcher = TestCoroutineDispatcher()

    operator fun invoke() = module {
        single(override = true) {
            CoroutineDispatchers(
                io = testDispatcher,
                main = testDispatcher,
                computation = testDispatcher
            )
        }
    }
}
