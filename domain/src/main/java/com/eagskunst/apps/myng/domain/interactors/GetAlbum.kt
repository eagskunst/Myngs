package com.eagskunst.apps.myng.domain.interactors

import com.eagskunst.apps.myng.domain.BaseInteractor
import com.eagskunst.apps.myngs.base.CoroutineDispatchers
import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.data.entities.relationships.AlbumWithSongs
import com.eagskunst.apps.myngs.data.repositories.album.AlbumRepository

/**
 * Created by eagskunst in 25/7/2020.
 */
class GetAlbum(
    private val albumRepository: AlbumRepository,
    dispatchers: CoroutineDispatchers
) : BaseInteractor(dispatchers) {

    suspend operator fun invoke(albumId: Long): DataResult<AlbumWithSongs> {
        val result = albumRepository.getAlbumWithSongsById(albumId)
        if (result is Success) {
            //TODO: Just save if it does not exist
            runAndForget {
                albumRepository.saveAlbumWithSongs(result.data)
            }
        }
        return result
    }
}