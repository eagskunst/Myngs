package com.eagskunst.apps.myngs.data.repositories.album

import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.data.entities.relationships.AlbumWithSongs
import com.eagskunst.apps.myngs.data.repositories.songs.SongsDataStore

/**
 * Created by eagskunst in 25/7/2020.
 */
class AlbumRepository(
    private val albumSource: AlbumDataSource,
    private val albumDataStore: AlbumDataStore,
    private val songsDataStore: SongsDataStore
) {

    suspend fun getAlbumWithSongsById(id: Long): DataResult<AlbumWithSongs> {
        val localAlbum = albumDataStore.getAlbumById(id)
        if (localAlbum != null && localAlbum.hasSongs) {
            return Success(albumDataStore.getAlbumWithSongs(id)!!)
        }

        return albumSource.getAlbumById(id)
    }

    suspend fun saveAlbumWithSongs(albumWithSongs: AlbumWithSongs) {
        albumDataStore.addAlbum(albumWithSongs.album)
        songsDataStore.addSongsWithIgnore(albumWithSongs.songs)
    }
}