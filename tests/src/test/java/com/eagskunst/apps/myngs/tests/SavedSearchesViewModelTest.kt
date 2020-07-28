package com.eagskunst.apps.myngs.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.ui.savedsearches.SavedSearchesViewModel
import com.eagskunst.apps.myngs.ui.savedsearches.SavedSearchesViewState
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

/**
 * Created by eagskunst in 26/7/2020.
 */
@ExperimentalCoroutinesApi
class SavedSearchesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule = TestCoroutineRule()

    private val searchTerm: SearchTerm = mockk()
    private val viewModel = SavedSearchesViewModel(searchTerm)

    @Test
    fun assertInitialState() {
        assertThat(viewModel.viewState.getOrAwaitValue().isLoading, `is`(true))
    }

    @Test
    fun whenUserHaveSearches_AssertListIsEmitted_AndErrorIsNone() {
        val savedSearches = mockk<List<Search>>()
        every { savedSearches.size } returns 2
        every { savedSearches.isEmpty() } returns false
        coEvery { searchTerm.getSavedSearches() } coAnswers {
            Success(savedSearches)
        }

        viewModel.getSavedSearches()
        val newState = viewModel.viewState.getOrAwaitValue()

        assertThat(
            newState.isLoading,
            `is`(false)
        )

        assertThat(
            newState.searches!!.size,
            `is`(2)
        )

        assertThat(
            viewModel.hasSearches(),
            `is`(true)
        )

        assert(newState.error == SavedSearchesViewState.Error.None)
    }

    @Test
    fun whenUserDoNotHaveSearches_AssertListIsNull_AndErrorIsEmptySearchError() {
        coEvery { searchTerm.getSavedSearches() } coAnswers {
            Success(listOf())
        }

        viewModel.getSavedSearches()
        val newState = viewModel.viewState.getOrAwaitValue()

        assert(newState.error == SavedSearchesViewState.Error.NoSearchesError)
        assert(newState.searches == null)

        assertThat(
            viewModel.hasSearches(),
            `is`(false)
        )
    }

    @Test
    fun whenUserHaveTwoSearches_AndDeletesOne_AssertThereIsOnlyOneLeft() {
        val dummySearch = mockk<Search>(relaxed = true)
        val deletableSearch = mockk<Search>(relaxed = true)
        val searches = mutableListOf(dummySearch, deletableSearch)
        coEvery { searchTerm.getSavedSearches() } coAnswers {
            Success(searches)
        }
        coEvery { searchTerm.deleteSearch(deletableSearch) } just Runs

        viewModel.getSavedSearches()
        viewModel.deleteSearch(deletableSearch)

        val currentState = viewModel.viewState.getOrAwaitValue()
        assertThat(currentState.searches!!.size, `is`(1))
    }

    @Test
    fun whenUserHaveTwoSearches_AndDeletesAll_AssertListIsNull_AndErrorIsEmptySearchError() {
        val searches = mutableListOf<Search>(mockk(relaxed = true), mockk(relaxed = true))
        coEvery { searchTerm.getSavedSearches() } coAnswers {
            Success(searches)
        }
        coEvery { searchTerm.deleteAllSearches() } just Runs

        viewModel.getSavedSearches()
        viewModel.deleteAllSearches()

        val newState = viewModel.viewState.getOrAwaitValue()

        assert(newState.error == SavedSearchesViewState.Error.NoSearchesError)
        assert(newState.searches == null)

        assertThat(
            viewModel.hasSearches(),
            `is`(false)
        )
    }
}
