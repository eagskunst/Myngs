package com.eagskunst.apps.myngs.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.ErrorResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.base.errors.EmptySearchException
import com.eagskunst.apps.myngs.data.mapper.TunesQueryToSongsMapper
import com.eagskunst.apps.myngs.data.responses.TunesQueryResponse
import com.eagskunst.apps.myngs.ui.home.HomeViewModel
import com.eagskunst.apps.myngs.ui.home.HomeViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.isA
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

/**
 * Created by eagskunst in 26/7/2020.
 */
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule = TestCoroutineRule()

    private val searchTerm: SearchTerm = mockk()
    val viewModel = HomeViewModel(searchTerm)
    val mapper = TunesQueryToSongsMapper()

    @Test
    fun assertInitialState() {
        assertThat(viewModel.viewState.getOrAwaitValue().initial, `is`(true))
    }

    @Test
    fun assertLoadingIsEmitted_AndInitialIsFalse() {
        coEvery { searchTerm.searchSentenceForSongs("something") } coAnswers {
            delay(2000)
            Success(
                mapper.map(
                    TunesQueryResponse(20, SampleData.sampleResponse())
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
                    TunesQueryResponse(20, SampleData.sampleResponse())
                )
            )
        }
        viewModel.searchForTerm("something")
        val newState = viewModel.viewState.getOrAwaitValue()
        assertThat(newState.songs!!.size, `is`(20))
        assertThat(newState.songs!!.first().name, `is`("song0"))
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

}