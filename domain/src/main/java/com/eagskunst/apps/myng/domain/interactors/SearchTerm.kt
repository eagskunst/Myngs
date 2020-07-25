package com.eagskunst.apps.myng.domain.interactors

import com.eagskunst.apps.myng.domain.BaseInteractor
import com.eagskunst.apps.myngs.base.*
import com.eagskunst.apps.myngs.base.errors.EmptySearchException
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.repositories.searchterm.SearchTermRepository

/**
 * Created by eagskunst in 25/7/2020.
 */
class SearchTerm(
    private val searchTermRepository: SearchTermRepository,
    dispatchers: CoroutineDispatchers
) : BaseInteractor(dispatchers) {


    suspend fun searchSentenceForSongs(sentence: String): DataResult<List<Song>> {
        val searchResult = searchTermRepository.searchSentence(sentence)

        if (searchResult is Success) {
            runAndForget {
                searchTermRepository.saveSongs(searchResult.data)
            }

            if (searchResult.data.isEmpty()) {
                val errorResult =
                    searchResult.mapToException(EmptySearchException("Term returned empty list"))

                return addErrorInformationAndReturn(errorResult)
            }
        }

        return searchResult
    }

}