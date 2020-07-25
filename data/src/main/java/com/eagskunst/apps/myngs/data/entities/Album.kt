package com.eagskunst.apps.myngs.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by eagskunst in 24/7/2020.
 */
@Entity(tableName = "albums")
data class Album(
    @PrimaryKey val id: Long,
    @ColumnInfo val name: String,
    @ColumnInfo(name = "creator_name") val creatorName: String,
    @ColumnInfo(name = "album_art_url") val albumArtUrl: String?
)