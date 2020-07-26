package com.eagskunst.apps.myngs.data.mapper

import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.responses.TunesQueryResponse

/**
 * Created by eagskunst in 24/7/2020.
 */
class TunesQueryToSongsMapper : Mapper<TunesQueryResponse, List<Song>> {

    var searchId: String? = null

    override suspend fun map(value: TunesQueryResponse): List<Song> {
        return value.results
            .filter { it.kind == "song" }
            .map {
                Song(
                    id = it.trackId!!,
                    name = it.trackName!!,
                    creatorName = it.artistName!!,
                    albumId = it.collectionId!!,
                    previewUrl = it.previewUrl,
                    albumName = it.collectionName,
                    artwork = it.artworkUrl100,
                    searchId = searchId
                )
            }
    }
}