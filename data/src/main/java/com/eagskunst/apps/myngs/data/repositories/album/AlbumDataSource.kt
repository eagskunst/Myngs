package com.eagskunst.apps.myngs.data.repositories.album

import com.eagskunst.apps.myngs.base.Asyncable
import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.base.thenMap
import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.mapper.TunesQueryToAlbum
import com.eagskunst.apps.myngs.data.services.AlbumService

/**
 * Created by eagskunst in 25/7/2020.
 */
class AlbumDataSource(
    private val albumService: AlbumService,
    private val albumMapper: TunesQueryToAlbum
) : Asyncable {
    suspend fun getAlbumById(id: Long): DataResult<Album> {
        return runSafely {
            albumService.getAlbumById(id = id)
        }.thenMap {
            albumMapper.map(it.results.first())
        }
    }
}