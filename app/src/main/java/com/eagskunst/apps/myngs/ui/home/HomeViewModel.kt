package com.eagskunst.apps.myngs.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.ErrorResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.base.errors.EmptySearchException
import com.eagskunst.apps.myngs.base_android.MyngsViewModel
import com.eagskunst.apps.myngs.data.entities.Song
import java.util.Locale
import kotlinx.coroutines.launch

/**
 * Created by eagskunst in 25/7/2020.
 */
class HomeViewModel(private val searchTerm: SearchTerm) : MyngsViewModel() {

    private val _viewState = MutableLiveData<HomeViewState>(HomeViewState(initial = true))
    val viewState = _viewState as LiveData<HomeViewState>

    fun searchForTerm(sentence: String) {
        viewModelScope.launch {
            var newState = viewState.value!!.copy(
                isLoading = true,
                initial = false,
                error = HomeViewState.Error.None
            )

            updateState(newState)
            // Change word to lower case, so searches with different case all return the same
            val result = searchTerm.searchSentenceForSongs(sentence.toLowerCase(Locale.ROOT))

            newState = when (result) {
                is Success -> newState.copy(songs = result.data)
                else -> newState.copy(error = mapError(result as ErrorResult<List<Song>>))
            }

            updateState(newState.copy(isLoading = false))
        }
    }

    private fun updateState(state: HomeViewState) {
        _viewState.value = state
    }

    private fun <T> mapError(errorResult: ErrorResult<T>) = when (errorResult.errorInfo.throwable) {
        is EmptySearchException -> HomeViewState.Error.EmptySearch
        else -> HomeViewState.Error.SearchFailed
    }
}
