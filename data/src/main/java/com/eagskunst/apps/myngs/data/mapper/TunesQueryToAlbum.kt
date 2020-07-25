package com.eagskunst.apps.myngs.data.mapper

import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.responses.TunesQueryResult

/**
 * Created by eagskunst in 24/7/2020.
 */
class TunesQueryToAlbum: Mapper<TunesQueryResult, Album> {

    override suspend fun map(value: TunesQueryResult): Album {
        return with(value) {
            Album(
                id = collectionId!!,
                creatorName = artistName!!,
                name = collectionName!!,
                albumArtUrl = artworkUrl100
            )
        }
    }
}