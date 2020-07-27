package com.eagskunst.apps.myngs.ui.savedsearches

import com.eagskunst.apps.myngs.data.entities.Search

/**
 * Created by eagskunst in 26/7/2020.
 */
data class SavedSearchesViewState(
    val isLoading: Boolean = true,
    val error: Error = Error.None,
    val searches: List<Search>? = null
) {
    sealed class Error {
        object None: Error()
        object NoSearchesError: Error()
    }
}