package com.eagskunst.apps.myngs.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.ui.savedsearches.SavedSearchesViewModel
import com.eagskunst.apps.myngs.ui.savedsearches.SavedSearchesViewState
import io.mockk.coEvery
import io.mockk.every
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
}
