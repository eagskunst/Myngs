package com.eagskunst.apps.myngs.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.Constants
import com.eagskunst.apps.myngs.base.ErrorResult
import com.eagskunst.apps.myngs.base.errors.EmptySearchException
import com.eagskunst.apps.myngs.base_android.MyngsViewModel
import com.eagskunst.apps.myngs.data.entities.relationships.SearchWithSongs

/**
 * Created by eagskunst in 25/7/2020.
 */
class HomeViewModel(private val searchTerm: SearchTerm) : MyngsViewModel() {


    private val _viewState = MediatorLiveData<HomeViewState>().apply { value = HomeViewState() }
    val viewState = _viewState as LiveData<HomeViewState>
    private var boundaryCallback: SearchBoundaryCallback? = null
    private var pagedListLiveData: LiveData<PagedList<SearchWithSongs>>? = null


    fun searchForTerm(sentence: String) {
        removeStateSources()
        boundaryCallback =
            SearchBoundaryCallback(sentence, viewModelScope, searchTerm, viewState.value!!.initial)
        pagedListLiveData = createPagedListLiveData(sentence, boundaryCallback!!)
        addSources(boundaryCallback!!, pagedListLiveData!!)
    }

    private fun addSources(
        boundaryCallback: SearchBoundaryCallback,
        pagedListLiveData: LiveData<PagedList<SearchWithSongs>>
    ) {
        with(_viewState) {
            var currentState = this.value!!
            addSource(boundaryCallback.viewState) { state ->
                currentState = state
                _viewState.value = state
            }
            addSource(pagedListLiveData) { pagedList ->
                currentState = currentState.copy(
                    songs = pagedList
                )
                _viewState.value = currentState
            }
        }
    }

    private fun createPagedListLiveData(
        sentence: String,
        boundaryCallback: SearchBoundaryCallback
    ): LiveData<PagedList<SearchWithSongs>> {
        return LivePagedListBuilder(
            searchTerm.getSearchesWithSongsDataSource(sentence),
            PagedList.Config
                .Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.Search.SEARCH_QUERY_LIMIT)
                .setInitialLoadSizeHint(Constants.Search.SEARCH_QUERY_LIMIT)
                .setMaxSize(Constants.Search.SEARCH_QUERY_MAX + 20)
                .setPrefetchDistance(15)
                .build()
        )
            .setBoundaryCallback(boundaryCallback)
            .build()
    }


    private fun removeStateSources() {
        boundaryCallback?.let { _viewState.removeSource(it.viewState) }
        pagedListLiveData?.let { _viewState.removeSource(it) }
    }

    private fun updateState(state: HomeViewState) {
        _viewState.value = state
    }

    private fun <T> mapError(errorResult: ErrorResult<T>) = when (errorResult.errorInfo.throwable) {
        is EmptySearchException -> HomeViewState.Error.EmptySearch
        else -> HomeViewState.Error.SearchFailed
    }
}
