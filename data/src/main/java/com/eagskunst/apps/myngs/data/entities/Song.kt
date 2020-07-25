package com.eagskunst.apps.myngs.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

/**
 * Created by eagskunst in 24/7/2020.
 */
@Entity(tableName = "songs", foreignKeys = [
    ForeignKey(
        entity = Album::class,
        parentColumns = ["id"],
        childColumns = ["album_id"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    ),
    ForeignKey(
        entity = Search::class,
        parentColumns = ["id"],
        childColumns = ["search_id"],
        onUpdate = CASCADE,
        onDelete = CASCADE
    )
])
data class Song(
    @PrimaryKey val id: Long,
    @ColumnInfo val name: String,
    @ColumnInfo(name = "creator_name") val creatorName: String,
    @ColumnInfo(name = "album_id") val albumId: Long,
    @ColumnInfo(name = "preview_url") val previewUrl: String,
    @ColumnInfo(name = "search_id") val searchId: String
)