package com.eagskunst.apps.myngs.ui.savedsearches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.base_android.MyngsViewModel
import kotlinx.coroutines.launch

/**
 * Created by eagskunst in 26/7/2020.
 */
class SavedSearchesViewModel(private val searchTerm: SearchTerm): MyngsViewModel() {

    private val _viewState = MutableLiveData<SavedSearchesViewState>(SavedSearchesViewState())
    val viewState = _viewState as LiveData<SavedSearchesViewState>

    fun getSavedSearches() {
        viewModelScope.launch {
            var newState = _viewState.value!!
            val savedSearches = searchTerm.getSavedSearches() as Success

            newState = if (savedSearches.data.isEmpty()) {
                newState.copy(error = SavedSearchesViewState.Error.NoSearchesError, searches = null)
            } else {
                newState.copy(searches = savedSearches.data, error = SavedSearchesViewState.Error.None)
            }
            _viewState.value = newState.copy(isLoading = false)
        }
    }

}