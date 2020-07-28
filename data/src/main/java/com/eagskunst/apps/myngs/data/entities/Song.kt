package com.eagskunst.apps.myngs.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

/**
 * Created by eagskunst in 24/7/2020.
 */
@Entity(tableName = "songs")
data class Song(
    @PrimaryKey val id: Long,
    @ColumnInfo val name: String,
    @ColumnInfo(name = "creator_name") val creatorName: String,
    @ColumnInfo(name = "album_id", index = true) val albumId: Long,
    @ColumnInfo(name = "preview_url") val previewUrl: String?,
    @ColumnInfo(name = "search_id", index = true) val searchId: String?,
    @ColumnInfo(name = "album_name") val albumName: String?,
    @ColumnInfo(name = "artwork") val artwork: String?,
    @ColumnInfo(name = "created_at") val createdAt: Long = Calendar.getInstance().timeInMillis
)
