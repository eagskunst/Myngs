package com.eagskunst.apps.myngs.data.repositories.searchterm

import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.data.entities.Song

/**
 * Created by eagskunst in 25/7/2020.
 */
class SearchTermRepository(private val searchDataSource: SearchDataSource) {

    suspend fun searchSentence(sentence: String): DataResult<List<Song>> {
        return searchDataSource.querySentenceForSongs(sentence)
    }

}