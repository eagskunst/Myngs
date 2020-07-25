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
    fun addSearch(search: Search)

    @Delete
    fun deleteSearch(search: Search)

    @Query("DELETE FROM searches WHERE search_id = :id LIMIT 1")
    fun deleteSearchById(id: Int)

    @Query("UPDATE searches SET stopped_at = :lastPage WHERE search_id = :searchId")
    fun updateStoppedAt(lastPage: Int, searchId: Int)

    @Query("DELETE FROM searches WHERE sentence = :sentence")
    fun deleteSearchBySentence(sentence: String)

    @Transaction
    @Query("SELECT * FROM searches WHERE sentence = :sentence LIMIT 1")
    fun getSearchWithSongs(sentence: String): SearchWithSongs

}