package com.eagskunst.apps.myngs.data.repositories.sources

import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.data.entities.Album

/**
 * Created by eagskunst in 25/7/2020.
 */
interface AlbumDataSource {
    suspend fun getAlbumById(id: Long): DataResult<Album>
}