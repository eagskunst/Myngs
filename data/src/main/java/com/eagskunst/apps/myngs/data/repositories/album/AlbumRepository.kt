package com.eagskunst.apps.myngs.data.repositories.album

import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.base.thenMap
import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.entities.relationships.AlbumWithSongs

/**
 * Created by eagskunst in 25/7/2020.
 */
class AlbumRepository(
    private val albumSource: AlbumDataSource,
    private val albumDataStore: AlbumDataStore
) {

    suspend fun getAlbumById(id: Long): DataResult<AlbumWithSongs> {
        val localAlbum = albumDataStore.getAlbumById(id)
        if (localAlbum != null) {
            return Success(albumDataStore.getAlbumWithSongs(id))
        }

        return albumSource.getAlbumById(id)
    }

    suspend fun saveAlbumWithSongs(albumWithSongs: AlbumWithSongs) {
        albumDataStore.addAlbum(albumWithSongs.album)
    }
}