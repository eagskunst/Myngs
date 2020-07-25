package com.eagskunst.apps.myngs.tests

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.data.MyngsDb
import com.eagskunst.apps.myngs.data.responses.TunesQueryResponse
import com.eagskunst.apps.myngs.data.services.SearchService
import com.eagskunst.apps.myngs.data_android.KoinModulesImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.inject
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

/**
 * Created by eagskunst in 25/7/2020.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class SearchTermTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val testDbModule = TestDatabaseModule()
    val dispatchersModule = DispatchersModule()

    private val database: MyngsDb by inject()
    private val searchTerms: SearchTerm by inject()
    private val searchService: SearchService = mockk()

    @get:Rule
    val koinRule = KoinTestRule.create {
        val context = ApplicationProvider.getApplicationContext<Context>()
        printLogger()
        modules(KoinModulesImpl.allButServicesDbAndBase() +
                testDbModule(context) + dispatchersModule() +
                module {
                    single(override = true) {
                        searchService
                    }
                }
        )
    }


    @ExperimentalCoroutinesApi
    @Test
    fun searchSentence_ReturnSuccess_AssertDbInsertions_AndResult() {
        coEvery {
            searchService.searchSentence(sentence = "in utero", page = 0)
        } returns TunesQueryResponse(results = SampleData.sampleResponse(), resultCount = 20)

        runBlocking {
            val res = searchTerms.searchSentenceForSongs("in utero")
            val songs = res.getOrThrow()
            val songsFromDb = database.songDao().getAllSongs()
            assert(res is Success)
            assert(songs.isNotEmpty())
            assertThat(songs.size, `is`(songsFromDb.size))
            assertThat(songs.first().name, `is`("song0"))
            assertThat(songs.first().id, `is`(songsFromDb.first().id))
            coVerify(exactly = 1){ searchService.searchSentence(sentence = "in utero", page = 0) }
        }

    }

}