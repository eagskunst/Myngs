package com.eagskunst.apps.myngs.data.repositories.sources

import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.data.entities.Song

/**
 * Created by eagskunst in 25/7/2020.
 */
interface SearchDataSource {
    suspend fun querySentenceForSongs(sentence: String): DataResult<List<Song>>
}