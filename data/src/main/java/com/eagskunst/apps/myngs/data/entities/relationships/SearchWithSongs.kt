package com.eagskunst.apps.myngs.data.entities.relationships

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Relation
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.data.entities.Song

/**
 * Created by eagskunst in 24/7/2020.
 */
data class SearchWithSongs(
    @Embedded val search: Search,
    @Relation(
        parentColumn = "search_id",
        entityColumn = "id"
    )
    val songs: List<Song>
)