package com.eagskunst.apps.myngs.data.repositories

import com.eagskunst.apps.myngs.base.Asyncable
import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.repositories.sources.SearchDataSource

/**
 * Created by eagskunst in 25/7/2020.
 */
class SearchTermRepository(private val searchDataSource: SearchDataSource) {

    suspend fun searchSentence(sentence: String): DataResult<List<Song>> {
        return searchDataSource.querySentenceForSongs(sentence)
    }

}