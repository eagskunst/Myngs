package com.eagskunst.apps.myngs.tests

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.eagskunst.apps.myngs.data.MyngsDb
import com.eagskunst.apps.myngs.data_android.KoinModulesImpl
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.inject
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

/**
 * Created by eagskunst in 25/7/2020.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class SearchTermTest: KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val testDbModule = TestDatabaseModule()

    val database: MyngsDb by inject()

    @get:Rule
    val koinRule = KoinTestRule.create {
        val context = ApplicationProvider.getApplicationContext<Context>()
        printLogger()
        modules(KoinModulesImpl.allButServicesDbAndBase() + testDbModule(context))
    }

    @Test
    fun sampleTest() {
        runBlocking {
            assertEquals(database.songDao().getAllSongs().size, 0)
        }
    }

}