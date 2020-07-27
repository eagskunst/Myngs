package com.eagskunst.apps.myngs.data.entities.relationships

import androidx.room.Embedded
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
        entityColumn = "search_id"
    )
    val songs: List<Song>
)
