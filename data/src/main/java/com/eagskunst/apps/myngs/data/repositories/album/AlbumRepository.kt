package com.eagskunst.apps.myngs.data.repositories.album

import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.repositories.album.AlbumDataSource

/**
 * Created by eagskunst in 25/7/2020.
 */
class AlbumRepository(
    private val albumSource: AlbumDataSource,
    private val albumDataStore: AlbumDataStore
) {

    suspend fun getAlbumById(id: Long): DataResult<Album> {
        val localAlbum = albumDataStore.getAlbumById(id)
        if (localAlbum != null) {
            return Success(localAlbum)
        }

        val albumResult = albumSource.getAlbumById(id)
        if (albumResult is Success) {
            //TODO deffer this call
            albumDataStore.addAlbum(albumResult.get())
        }

        return albumResult
    }
}