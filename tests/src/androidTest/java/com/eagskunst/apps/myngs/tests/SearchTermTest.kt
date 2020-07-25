package com.eagskunst.apps.myngs.tests

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.ErrorMessage
import com.eagskunst.apps.myngs.base.ErrorResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.base.errors.EmptySearchException
import com.eagskunst.apps.myngs.data.MyngsDb
import com.eagskunst.apps.myngs.data.responses.TunesQueryResponse
import com.eagskunst.apps.myngs.data.services.SearchService
import com.eagskunst.apps.myngs.data_android.KoinModulesImpl
import com.eagskunst.apps.myngs.tests.robots.ConditionRobot
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
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.koin.core.inject
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by eagskunst in 25/7/2020.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class SearchTermTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val expectedException = ExpectedException.none()

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
        val sentence = "in utero"
        coEvery {
            searchService.searchSentence(sentence = sentence, page = 0)
        } returns TunesQueryResponse(results = SampleData.sampleResponse(), resultCount = 20)

        val cr = ConditionRobot()
        runBlocking {
            val res = searchTerms.searchSentenceForSongs(sentence)
            val songs = res.getOrThrow()
            cr.waitUntil(waitMillisPerTry = 350L, maxTries = 25) {
                database.songDao().getAllSongs().isNotEmpty()
            }
            val songsFromDb = database.songDao().getAllSongs()
            assert(res is Success)
            assert(songs.isNotEmpty())
            assertThat(songs.size, `is`(songsFromDb.size))
            assertThat(songs.first().name, `is`("song0"))
            assertThat(songs.first().id, `is`(songsFromDb.first().id))
            coVerify(exactly = 1) { searchService.searchSentence(sentence = sentence, page = 0) }
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun persistenceAndServiceVerificationForNotEmptyResult() {
        searchSentence_ReturnSuccess_VerifySearchWasSavedWithNotEmpty()
        searchRepeatedSentence_VerifyServiceIsNotCalledAgain()
    }

    private fun searchSentence_ReturnSuccess_VerifySearchWasSavedWithNotEmpty() {
        val sentence = "dumb"
        coEvery {
            searchService.searchSentence(sentence = sentence, page = 0)
        } returns TunesQueryResponse(results = SampleData.sampleResponse(), resultCount = 20)


        runBlocking {
            val res = searchTerms.searchSentenceForSongs(sentence)
            val search = database.searchDao().getSearchBySentence(sentence)
            assert(search != null) {
                "The search was not saved after being executed"
            }
            assert(!search!!.isEmptySearch)
        }

    }

    private fun searchRepeatedSentence_VerifyServiceIsNotCalledAgain() {
        val sentence = "dumb"
        runBlocking {
            searchTerms.searchSentenceForSongs(sentence)
            coVerify(exactly = 1) { searchService.searchSentence(sentence = sentence, page = 0) }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun persistenceAndServiceVerificationForEmptyResult() {
        searchSentence_ReturnError_VerifySearchWasSavedWithEmpty_AndErrorMessageIsTermNotFound()
        searchRepeatedSentence_ThatReturnedEmptyList_VerifyServiceIsNotCalledAgain_AndResultTheListIsEmpty()
    }


    private fun searchSentence_ReturnError_VerifySearchWasSavedWithEmpty_AndErrorMessageIsTermNotFound() {
        val sentence = "syre"
        coEvery {
            searchService.searchSentence(sentence = sentence, page = 0)
        } returns TunesQueryResponse(results = listOf(), resultCount = 0)

        expectedException.expect(EmptySearchException::class.java)

        runBlocking {
            val res = searchTerms.searchSentenceForSongs(sentence)
            val search = database.searchDao().getSearchBySentence(sentence)
            assert(search == null) {
                "The search was not saved after being executed"
            }
            assert(search!!.isEmptySearch)
            assert(res is ErrorResult)
            assertThat( (res as ErrorResult).errorInfo.message, `is`(ErrorMessage.TermNotFound) )
        }
    }

    private fun searchRepeatedSentence_ThatReturnedEmptyList_VerifyServiceIsNotCalledAgain_AndResultTheListIsEmpty() {
        val sentence = "syre"
        runBlocking {
            val res = searchTerms.searchSentenceForSongs(sentence)
            coVerify(exactly = 1) { searchService.searchSentence(sentence = sentence, page = 0) }
            assert(res.getOrThrow().isEmpty())
        }
    }

    @Test
    fun searchSentence_ReturnsListWithoutSongs_AssertTheResultListIsEmpty() {
        val sentence = "big bang theory"
        coEvery { searchService.searchSentence(sentence, page = 0) } returns TunesQueryResponse(
            resultCount = 20,
            results = SampleData.sampleResponse(kind = "collection")
        )

        runBlocking {
            val res = searchTerms.searchSentenceForSongs(sentence)
            assert(res is ErrorResult) {
                "The result was a success. The mapper let pass a query result of type 'collection'"
            }
        }
    }

    @Test
    fun searchSentence_ThrowException_AssertSearchWasNotSaved() {
        val sentence = "a long sentence that will need time"
        coEvery { searchService.searchSentence(sentence, page = 0) } throws SocketTimeoutException("Timeout")

        runBlocking {
            val res = searchTerms.searchSentenceForSongs(sentence)
            assert(res is ErrorResult) {
                "The result was a success. Test was expecting an SocketTimeout"
            }
            val savedSearch = database.searchDao().getSearchBySentence(sentence)
            assert(savedSearch == null) {
                "The search must not be saved if the service throw an exception"
            }
        }
    }

}