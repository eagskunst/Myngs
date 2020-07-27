package com.eagskunst.apps.myngs.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.entities.relationships.AlbumWithSongs

/**
 * Created by eagskunst in 24/7/2020.
 */
@Dao
interface AlbumDao {

    @Insert(onConflict = REPLACE)
    suspend fun addAlbum(album: Album)

    @Delete
    suspend fun deleteAlbum(album: Album)

    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: Long): Album?

    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<Album>

    @Transaction
    @Query("SELECT * FROM albums WHERE id = :albumId LIMIT 1")
    suspend fun getAlbumWithSongs(albumId: Long): AlbumWithSongs?
}
