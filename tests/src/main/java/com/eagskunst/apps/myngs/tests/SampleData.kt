package com.eagskunst.apps.myngs.tests

import androidx.annotation.VisibleForTesting
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.responses.TunesQueryResult

/**
 * Created by eagskunst in 25/7/2020.
 */
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object SampleData {

    fun sampleResponse() = (0 until 20).map {
        TunesQueryResult(
            trackId = it.toLong(),
            trackName = "song$it",
            artistName = "creator$it",
            collectionId = (it+1).toLong(),
            previewUrl = "",
            kind = "song"
        )
    }
}