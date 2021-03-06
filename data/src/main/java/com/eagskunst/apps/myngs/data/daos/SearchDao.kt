package com.eagskunst.apps.myngs.data.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.entities.relationships.SearchWithSongs

/**
 * Created by eagskunst in 24/7/2020.
 */
@Dao
interface SearchDao {

    @Insert(onConflict = REPLACE)
    suspend fun addSearch(search: Search)

    @Delete
    suspend fun deleteSearch(search: Search)

    @Query("DELETE FROM searches")
    suspend fun deleteAll()

    @Query("SELECT * FROM searches WHERE sentence = :sentence LIMIT 1")
    suspend fun getSearchBySentence(sentence: String): Search?

    @Query("UPDATE searches SET started_from = :lastPage WHERE search_id = :searchId")
    suspend fun updateStartedFrom(lastPage: Int, searchId: String)

    @Query("DELETE FROM searches WHERE search_id = :id ")
    suspend fun deleteSearchById(id: String)

    @Query("DELETE FROM searches WHERE sentence = :sentence")
    suspend fun deleteSearchBySentence(sentence: String)

    @Transaction
    @Query("SELECT * FROM searches WHERE sentence = :sentence LIMIT 1")
    suspend fun getSearchWithSongs(sentence: String): SearchWithSongs?

    @Transaction
    @Query("""
        SELECT id, name, creator_name, album_id, preview_url, songs.search_id, album_name, artwork, songs.created_at
        FROM searches AS srch INNER JOIN songs WHERE srch.sentence = :sentence AND songs.search_id = srch.search_id
        ORDER BY songs.created_at ASC
        """
    )
    fun getSearchWithSongsBySentence(sentence: String): DataSource.Factory<Int, Song>

    @Query("SELECT * FROM searches WHERE isEmptySearch = 0 ORDER BY created_at DESC")
    suspend fun getNotEmptySearches(): List<Search>
}
