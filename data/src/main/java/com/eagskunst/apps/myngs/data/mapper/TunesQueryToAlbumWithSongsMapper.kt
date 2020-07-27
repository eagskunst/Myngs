package com.eagskunst.apps.myngs.data.mapper

import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.entities.relationships.AlbumWithSongs
import com.eagskunst.apps.myngs.data.responses.TunesQueryResponse

/**
 * Created by eagskunst in 24/7/2020.
 */
class TunesQueryToAlbumWithSongsMapper(private val songsMapper: TunesQueryToSongsMapper) :
    Mapper<TunesQueryResponse, AlbumWithSongs> {

    override suspend fun map(value: TunesQueryResponse): AlbumWithSongs {
        val album = with(value.results.first()) {
            Album(
                id = collectionId!!,
                creatorName = artistName!!,
                name = collectionName!!,
                albumArtUrl = artworkUrl100,
                hasSongs = value.resultCount > 1
            )
        }
        val songs = songsMapper.map(value)

        return AlbumWithSongs(
            album,
            songs
        )
    }
}
