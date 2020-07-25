package com.eagskunst.apps.myngs.data.repositories.searchterm

import com.eagskunst.apps.myngs.base.Asyncable
import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.base.thenMap
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.mapper.TunesQueryToSongsMapper
import com.eagskunst.apps.myngs.data.services.SearchService

/**
 * Created by eagskunst in 25/7/2020.
 */
class SearchDataSource(
    private val searchService: SearchService,
    private val mapper: TunesQueryToSongsMapper
) : Asyncable {

    suspend fun querySentenceForSongs(search: Search): DataResult<List<Song>> {
        mapper.searchId = search.id
        return runSafely {
            searchService.searchSentence(sentence = search.sentence, page = search.stoppedAt)
        }.thenMap(mapper::map)
    }

}