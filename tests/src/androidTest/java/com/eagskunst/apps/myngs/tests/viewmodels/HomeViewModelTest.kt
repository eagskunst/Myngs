package com.eagskunst.apps.myngs.tests.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.ErrorResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.base.errors.EmptySearchException
import com.eagskunst.apps.myngs.data.entities.relationships.SearchWithSongs
import com.eagskunst.apps.myngs.data.mapper.TunesQueryToSongsMapper
import com.eagskunst.apps.myngs.data.responses.TunesQueryResponse
import com.eagskunst.apps.myngs.tests.SampleData
import com.eagskunst.apps.myngs.tests.TestCoroutineRule
import com.eagskunst.apps.myngs.tests.createMockDataSourceFactory
import com.eagskunst.apps.myngs.tests.getOrAwaitValue
import com.eagskunst.apps.myngs.ui.home.HomeViewModel
import com.eagskunst.apps.myngs.ui.home.HomeViewState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by eagskunst in 26/7/2020.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule = TestCoroutineRule()

    private val searchTerm: SearchTerm = mockk()
    lateinit var viewModel: HomeViewModel
    val mapper = TunesQueryToSongsMapper()

    @Before
    fun setup() {
        val songs = runBlocking {
            mapper.map(
                TunesQueryResponse(20,
                    SampleData.sampleResponse()
                )
            )
        }
        val searchWithSongs = mockk<SearchWithSongs>()
        every { searchWithSongs.songs } returns songs
        val dsFactory = createMockDataSourceFactory<SearchWithSongs>(listOf(searchWithSongs))

        every { searchTerm.getSearchesWithSongsDataSource(any()) } returns dsFactory
        viewModel = HomeViewModel(searchTerm)
    }

    @Test
    fun assertInitialState() {
        assertThat(viewModel.viewState.getOrAwaitValue().initial, `is`(true))
    }

    @Test
    fun assertLoadingIsEmitted_AndInitialIsFalse() {
        coEvery { searchTerm.searchSentenceForSongs("something") } coAnswers {
            delay(5000)
            Success(
                mapper.map(
                    TunesQueryResponse(20,
                        SampleData.sampleResponse()
                    )
                )
            )
        }
        viewModel.searchForTerm("something")
        assertThat(viewModel.viewState.getOrAwaitValue().initial, `is`(false))
        assertThat(viewModel.viewState.getOrAwaitValue().isLoading, `is`(true))
        coroutineRule.runBlockingTest {
            delay(2500)
        }
    }

    @Test
    fun whenSuccessResult_AssertListIsEmitted() {
        coEvery { searchTerm.searchSentenceForSongs("something") } coAnswers {
            Success(
                mapper.map(
                    TunesQueryResponse(20,
                        SampleData.sampleResponse()
                    )
                )
            )
        }
        viewModel.searchForTerm("something")
        val newState = viewModel.viewState.getOrAwaitValue()
        assertThat(newState.songs!![0]!!.songs.size, `is`(20))
        assertThat(newState.songs!![0]!!.songs.first().name, `is`("song0"))
    }

    @Test
    fun whenErrorResult_AssertErrorIsEmitted() {
        coEvery { searchTerm.searchSentenceForSongs("something") } coAnswers {
            ErrorResult(
                throwable = EmptySearchException("query returned empty array")
            )
        }
        viewModel.searchForTerm("something")
        val newState = viewModel.viewState.getOrAwaitValue()
        assert(newState.error is HomeViewState.Error.EmptySearch)
    }

    @Test
    fun whenErrorIsEmitted_AndThenNewQueryIsDone_AssertSuccessStateWithoutErrors() {
        coEvery { searchTerm.searchSentenceForSongs("something") } coAnswers {
            ErrorResult(
                throwable = EmptySearchException("query returned empty array")
            )
        } coAndThen {
            Success(
                mapper.map(
                    TunesQueryResponse(20,
                        SampleData.sampleResponse()
                    )
                )
            )
        }

        viewModel.searchForTerm("something")
        var currentState = viewModel.viewState.getOrAwaitValue()
        assert(currentState.error is HomeViewState.Error.EmptySearch)
        viewModel.searchForTerm("something")
        currentState = viewModel.viewState.getOrAwaitValue()
        assert(currentState.error is HomeViewState.Error.None)
        assertThat(currentState.songs!![0]!!.songs.first().name, `is`("song0"))
    }
}
