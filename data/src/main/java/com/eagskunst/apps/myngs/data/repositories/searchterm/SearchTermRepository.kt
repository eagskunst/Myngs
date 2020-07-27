package com.eagskunst.apps.myngs.data.repositories.searchterm

import com.eagskunst.apps.myngs.base.Constants
import com.eagskunst.apps.myngs.base.DataResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.entities.relationships.SearchWithSongs
import com.eagskunst.apps.myngs.data.repositories.album.AlbumDataStore
import com.eagskunst.apps.myngs.data.repositories.songs.SongsDataStore

/**
 * Created by eagskunst in 25/7/2020.
 */
class SearchTermRepository(
    private val searchDataSource: SearchDataSource,
    private val searchDataStore: SearchDataStore,
    private val albumDataStore: AlbumDataStore,
    private val songsDataStore: SongsDataStore
) {

    suspend fun searchSentence(sentence: String): DataResult<List<Song>> {

        // If the search exist and is empty, return empty result
        searchDataStore.getSearchBySentence(sentence)?.let {
            if (it.isEmptySearch) return Success(emptyList())
        }

        val searchWithSongs = searchDataStore.getSearchWithSongs(sentence)

        if (searchWithSongs != null) {
            return getMoreSongsFromSearch(searchWithSongs)
        }

        // If there is no search, create a new one
        val newSearch = Search(sentence = sentence, startedFrom = 0)
        val result = searchDataSource.querySentenceForSongs(newSearch)

        if (result is Success) {
            val emptySearch = result.data.isEmpty()
            searchDataStore.addSearch(newSearch.copy(isEmptySearch = emptySearch, startedFrom = result.data.size))
        }

        return result
    }

    private suspend fun getMoreSongsFromSearch(searchWithSongs: SearchWithSongs): DataResult<List<Song>> {
        val searchStartedFrom = searchWithSongs.search.startedFrom
        if (searchStartedFrom > Constants.Search.SEARCH_QUERY_MAX) {
            return Success(searchWithSongs.songs)
        }
        val search = searchWithSongs.search
        val result = searchDataSource.querySentenceForSongs(search)

        if (result is Success) {
            searchDataStore.updateStoppedAt(searchWithSongs.search.id, searchStartedFrom + result.data.size)
        }

        return result
    }

    suspend fun saveAlbumBasedOnSong(song: Song) {
        albumDataStore.addAlbumOfSong(song)
    }

    suspend fun saveSongs(songs: List<Song>) {
        songsDataStore.addSongs(songs)
    }

    suspend fun savedSearches(): DataResult<List<Search>> {
        return Success(searchDataStore.getSearches())
    }

    suspend fun retrieveSavedSearchWithSongs(searchId: String) =
        searchDataStore.getSearchWithSongs(searchId)

    fun getSearchesWithSongs(sentence: String) =
        searchDataStore.getSearchesWithSongsDataSource(sentence)
}
