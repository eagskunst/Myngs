package com.eagskunst.apps.myngs.data.entities.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.entities.Song

/**
 * Created by eagskunst in 25/7/2020.
 */
data class AlbumWithSongs(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "album_id"
    )
    val songs: List<Song>
)
