package com.eagskunst.apps.myngs.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.ErrorResult
import com.eagskunst.apps.myngs.base.errors.EmptySearchException
import com.eagskunst.apps.myngs.base.errors.NoMoreItemsException
import com.eagskunst.apps.myngs.data.entities.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by eagskunst in 27/7/2020.
 */

class SearchBoundaryCallback(
    private val sentence: String,
    private val scope: CoroutineScope,
    private val searchTerm: SearchTerm,
    initialState: Boolean
): PagedList.BoundaryCallback<Song>() {

    private val _viewState = MutableLiveData<HomeViewState>(HomeViewState(initial = initialState))
    val viewState = _viewState as LiveData<HomeViewState>
    var noMoreItemsExceptionReceived = false

    override fun onZeroItemsLoaded() {
        requestSongsForSentence()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Song) {
        requestSongsForSentence()
    }

    private fun requestSongsForSentence() {
        if (noMoreItemsExceptionReceived) {
            return
        }

        var currentState = _viewState.value!!.copy(
            isLoading = true,
            initial = false,
            error = HomeViewState.Error.None
        )
        updateState(currentState)
        scope.launch {
            val result = searchTerm.searchSentenceForSongs(sentence.toLowerCase(Locale.ROOT))
            currentState = currentState.copy(isLoading = false)
            updateState(currentState)

            if (result is ErrorResult) {
                currentState = currentState.copy(error = mapError(result))
                updateState(currentState)
            }

        }
    }

    private fun updateState(newState: HomeViewState) {
        _viewState.value = newState
    }

    private fun <T> mapError(errorResult: ErrorResult<T>) = when (errorResult.errorInfo.throwable) {
        is EmptySearchException -> HomeViewState.Error.EmptySearch
        is NoMoreItemsException -> {
            noMoreItemsExceptionReceived = true
            HomeViewState.Error.NoMoreItems
        }
        else -> HomeViewState.Error.SearchFailed
    }

}