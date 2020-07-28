package com.eagskunst.apps.myngs.ui.savedsearches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.base_android.MyngsViewModel
import com.eagskunst.apps.myngs.data.entities.Search
import kotlinx.coroutines.launch

/**
 * Created by eagskunst in 26/7/2020.
 */
class SavedSearchesViewModel(private val searchTerm: SearchTerm) : MyngsViewModel() {

    private val _viewState = MutableLiveData<SavedSearchesViewState>(SavedSearchesViewState())
    val viewState = _viewState as LiveData<SavedSearchesViewState>

    fun getSavedSearches() {
        viewModelScope.launch {
            val newState = _viewState.value!!
            val savedSearches = searchTerm.getSavedSearches() as Success

            updateWithSearches(savedSearches.data, newState)
        }
    }

    fun hasSearches() = _viewState.value!!.searches != null && _viewState.value!!.searches!!.isNotEmpty()

    fun deleteSearch(search: Search) {
        viewModelScope.launch {
            searchTerm.deleteSearch(search)
            val currenState = _viewState.value!!
            val currentSearches = currenState.searches?.toMutableList()
                ?.run {
                    remove(search)
                    toList()
                }
            updateWithSearches(currentSearches, currenState)
        }
    }

    fun deleteAllSearches() {
        viewModelScope.launch {
            searchTerm.deleteAllSearches()
            updateWithSearches(null, _viewState.value!!)
        }
    }

    private fun updateWithSearches(currentSearches: List<Search>?, currenState: SavedSearchesViewState) {
        val newState = if (currentSearches == null || currentSearches.isEmpty()) {
            currenState.copy(error = SavedSearchesViewState.Error.NoSearchesError, searches = null)
        } else {
            currenState.copy(searches = currentSearches, error = SavedSearchesViewState.Error.None)
        }
        _viewState.value = newState.copy(isLoading = false)
    }
}
