package com.eagskunst.apps.myngs.ui.home

import androidx.paging.PagedList
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.entities.relationships.SearchWithSongs

/**
 * Created by eagskunst in 25/7/2020.
 */
data class HomeViewState(
    val isLoading: Boolean = false,
    val error: Error = Error.None,
    val songs: PagedList<SearchWithSongs>? = null,
    val initial: Boolean = false
) {

    sealed class Error {
        object SearchFailed : Error()
        object EmptySearch : Error()
        object None : Error()
    }
}
