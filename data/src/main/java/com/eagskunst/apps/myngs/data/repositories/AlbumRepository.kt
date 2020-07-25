package com.eagskunst.apps.myngs.data.repositories

import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.repositories.sources.AlbumDataSource

/**
 * Created by eagskunst in 25/7/2020.
 */
class AlbumRepository(private val albumDataSource: AlbumDataSource) {

    suspend fun getAlbumById(id: Long): DataResult<Album> {
        return albumDataSource.getAlbumById(id)
    }
}