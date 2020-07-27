package com.eagskunst.apps.myngs.data.mapper

import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.entities.Song

/**
 * Created by eagskunst in 25/7/2020.
 */
class SongToAlbumMapper : Mapper<Song, Album> {
    override suspend fun map(value: Song): Album {
        return with(value) {
            Album(
                id = albumId,
                hasSongs = false,
                creatorName = creatorName,
                name = "",
                albumArtUrl = null
            )
        }
    }
}
