package com.eagskunst.apps.myngs.ui.albumdetail

import com.eagskunst.apps.myngs.data.entities.relationships.AlbumWithSongs

/**
 * Created by eagskunst in 26/7/2020.
 */
data class AlbumDetailViewState(
    val isLoading: Boolean = false,
    val error: Error = Error.None,
    val initial: Boolean = true,
    val albumWithSongs: AlbumWithSongs? = null
) {
    sealed class Error {
        object Network: Error()
        object EmptyAlbum: Error()
        object None: Error()
    }
}