package com.eagskunst.apps.myngs.data.repositories.songs

import com.eagskunst.apps.myngs.data.daos.SongDao
import com.eagskunst.apps.myngs.data.entities.Song

/**
 * Created by eagskunst in 25/7/2020.
 */
class SongsDataStore(private val songsDao: SongDao) {

    suspend fun addSong(song: Song) = songsDao.addSong(song)
    suspend fun addSongs(songs: List<Song>) = songsDao.addSongs(songs)
    suspend fun addSongsWithIgnore(songs: List<Song>) = songsDao.addSongsWithIgnore(songs)
    suspend fun updateSong(song: Song) = songsDao.updateSong(song)
    suspend fun deleteSong(song: Song) = songsDao.deleteSong(song)
}
