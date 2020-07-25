package com.eagskunst.apps.myngs.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Update
import com.eagskunst.apps.myngs.data.entities.Song

/**
 * Created by eagskunst in 24/7/2020.
 */
@Dao
interface SongDao {

    @Insert(onConflict = REPLACE)
    suspend fun addSong(song: Song)

    @Insert(onConflict = REPLACE)
    suspend fun addSongs(song: List<Song>)

    @Update
    suspend fun updateSong(song: Song)

    @Delete
    suspend fun deleteSong(song: Song)

}