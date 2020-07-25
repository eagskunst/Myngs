package com.eagskunst.apps.myngs.data.daos

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.eagskunst.apps.myngs.data.entities.Album

/**
 * Created by eagskunst in 24/7/2020.
 */
@Dao
interface AlbumDao {

    @Insert(onConflict = REPLACE)
    fun addAlbum(album: Album)

    @Delete
    fun deleteAlbum(album: Album)

    @Query("SELECT * FROM albums WHERE id = :id")
    fun getAlbumById(id: Long): Album

    @Query("SELECT * FROM albums")
    fun getAllAlbums(): List<Album>
}