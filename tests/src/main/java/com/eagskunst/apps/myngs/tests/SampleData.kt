package com.eagskunst.apps.myngs.tests

import androidx.annotation.VisibleForTesting
import com.eagskunst.apps.myngs.base.Constants
import com.eagskunst.apps.myngs.data.responses.TunesQueryResult

/**
 * Created by eagskunst in 25/7/2020.
 */
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object SampleData {

    fun sampleResponse(kind: String = "song", size: Int = Constants.Search.SEARCH_QUERY_LIMIT) = (0 until size).map {
        TunesQueryResult(
            trackId = it.toLong(),
            trackName = "song$it",
            artistName = "creator$it",
            collectionId = (it + 1).toLong(),
            previewUrl = "",
            kind = kind
        )
    }

    /**
     *
    id = collectionId!!,
    creatorName = artistName!!,
    name = collectionName!!,
    albumArtUrl = artworkUrl100,
     */

    fun sampleResponseWithAlbumFirst(albumId: Long = 900L): List<TunesQueryResult> {
        val artistName = "mocked_artist"
        val albumName = "mocked_album"
        return (0 until 20).map {
            if (it == 0) {
                TunesQueryResult(
                    collectionName = albumName,
                    artistName = artistName,
                    collectionId = albumId
                )
            } else {
                TunesQueryResult(
                    trackId = it.toLong(),
                    trackName = "song$it",
                    artistName = "creator$it",
                    collectionId = albumId,
                    previewUrl = "",
                    kind = "song"
                )
            }
        }
    }
}
