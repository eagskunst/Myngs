package com.eagskunst.apps.myngs.data.repositories.album

import com.eagskunst.apps.myngs.data.daos.AlbumDao
import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.mapper.SongToAlbumMapper

/**
 * Created by eagskunst in 25/7/2020.
 */
class AlbumDataStore(private val albumDao: AlbumDao, private val songToAlbumMapper: SongToAlbumMapper) {

    suspend fun getAlbumById(id: Long) = albumDao.getAlbumById(id)
    suspend fun addAlbum(album: Album) = albumDao.addAlbum(album)
    suspend fun addAlbumOfSong(song: Song) = albumDao.addAlbum(songToAlbumMapper.map(song))
    suspend fun getAlbumWithSongs(id: Long) = albumDao.getAlbumWithSongs(albumId = id)
}
