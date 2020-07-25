package com.eagskunst.apps.myngs.data.daos

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.eagskunst.apps.myngs.data.entities.Search
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

    @Query("SELECT * FROM searches WHERE sentence = :sentence LIMIT 1")
    suspend fun getSearchBySentence(sentence: String): Search?

    @Query("UPDATE searches SET stopped_at = :lastPage WHERE search_id = :searchId")
    suspend fun updateStoppedAt(lastPage: Int, searchId: Int)

    @Query("DELETE FROM searches WHERE search_id = :id LIMIT 1")
    suspend fun deleteSearchById(id: Int)

    @Query("DELETE FROM searches WHERE sentence = :sentence")
    suspend fun deleteSearchBySentence(sentence: String)

    @Transaction
    @Query("SELECT * FROM searches WHERE sentence = :sentence LIMIT 1")
    suspend fun getSearchWithSongs(sentence: String): SearchWithSongs?

}