package com.eagskunst.apps.myngs.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by eagskunst in 24/7/2020.
 */
@Entity(tableName = "searches")
data class Search(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "search_id") val id: Int = 0,
    @ColumnInfo val sentence: String,
    @ColumnInfo(name = "stopped_at") val stoppedAt: Int,
    @ColumnInfo val isEmptySearch: Boolean = false
)