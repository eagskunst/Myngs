package com.eagskunst.apps.myngs.data.repositories.searchterm

import com.eagskunst.apps.myngs.data.daos.SearchDao
import com.eagskunst.apps.myngs.data.entities.Search

/**
 * Created by eagskunst in 25/7/2020.
 */
class SearchDataStore(private val searchDao: SearchDao) {

    suspend fun addSearch(search: Search) = searchDao.addSearch(search)

    suspend fun getSearchBySentence(sentence: String) = searchDao.getSearchBySentence(sentence)

    suspend fun getSearchWithSongs(sentence: String) = searchDao.getSearchWithSongs(sentence)

    suspend fun updateStoppedAt(searchId: String, lastPage: Int) =
        searchDao.updateStartedFrom(lastPage, searchId)

    suspend fun getSearches() = searchDao.getNotEmptySearches()

    fun getSearchesWithSongsDataSource(sentence: String) =
        searchDao.getSearchWithSongsBySentence(sentence)

    suspend fun deleteSearch(search: Search) = searchDao.deleteSearch(search)

    suspend fun deleteAllSearches() = searchDao.deleteAll()
}
