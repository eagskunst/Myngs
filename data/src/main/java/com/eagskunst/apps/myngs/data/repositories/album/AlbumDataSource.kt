package com.eagskunst.apps.myngs.data.repositories.album

import com.eagskunst.apps.myngs.base.Asyncable
import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.base.thenMap
import com.eagskunst.apps.myngs.data.entities.relationships.AlbumWithSongs
import com.eagskunst.apps.myngs.data.mapper.TunesQueryToAlbumWithSongs
import com.eagskunst.apps.myngs.data.services.AlbumService

/**
 * Created by eagskunst in 25/7/2020.
 */
class AlbumDataSource(
    private val albumService: AlbumService,
    private val albumMapper: TunesQueryToAlbumWithSongs
) : Asyncable {

    suspend fun getAlbumById(id: Long): DataResult<AlbumWithSongs> {
        return runSafely {
            albumService.getAlbumById(id = id)
        }.thenMap {
            albumMapper.map(it)
        }
    }
}