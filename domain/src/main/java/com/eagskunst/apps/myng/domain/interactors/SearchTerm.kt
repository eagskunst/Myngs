package com.eagskunst.apps.myng.domain.interactors

import com.eagskunst.apps.myng.domain.BaseInteractor
import com.eagskunst.apps.myngs.base.CoroutineDispatchers
import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.base.ErrorResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.base.errors.EmptySearchException
import com.eagskunst.apps.myngs.base.mapToException
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.entities.relationships.SearchWithSongs
import com.eagskunst.apps.myngs.data.repositories.searchterm.SearchTermRepository

/**
 * Created by eagskunst in 25/7/2020.
 */
class SearchTerm(
    private val searchTermRepository: SearchTermRepository,
    dispatchers: CoroutineDispatchers
) : BaseInteractor(dispatchers) {

    suspend fun searchSentenceForSongs(sentence: String): DataResult<List<Song>> {
        val searchResult = switchToIoWithResult {
            searchTermRepository.searchSentence(sentence)
        }

        if (searchResult is Success) {
            runAndForget {
                searchTermRepository.saveSongs(searchResult.data)
            }

            if (searchResult.data.isEmpty()) {
                val errorResult =
                    searchResult.mapToException(EmptySearchException("Term returned empty list"))

                return addErrorInformationToResult(errorResult)
            }
        }

        return searchResult
    }

    suspend fun getSavedSearches(): DataResult<List<Search>> = switchToIoWithResult {
        searchTermRepository.savedSearches()
    }

    suspend fun getSavedSearch(searchId: String): DataResult<SearchWithSongs> {
        val searchWithSongs = searchTermRepository.retrieveSavedSearchWithSongs(searchId)
        searchWithSongs?.let { return Success(it) }

        return addErrorInformationToResult(ErrorResult(EmptySearchException("Term returned empty list")))
    }

    fun getSearchesWithSongsDataSource(sentence: String) =
        searchTermRepository.getSearchesWithSongs(sentence)
}
