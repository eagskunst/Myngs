package com.eagskunst.apps.myng.domain.interactors

import com.eagskunst.apps.myng.domain.BaseInteractor
import com.eagskunst.apps.myngs.base.CoroutineDispatchers
import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.repositories.searchterm.SearchTermRepository

/**
 * Created by eagskunst in 25/7/2020.
 */
class SearchTerm (
    private val searchTermRepository: SearchTermRepository,
    dispatchers: CoroutineDispatchers
) : BaseInteractor(dispatchers) {


    suspend fun searchSentenceForSongs(sentence: String): DataResult<List<Song>> {
        val searchResult = searchTermRepository.searchSentence(sentence)

        if (searchResult is Success) {
            switchToIo {
                //TODO just save if they don't exist
                runAndForget { searchTermRepository.saveSongs(searchResult.data) }
            }
        }

        return searchResult
    }

}