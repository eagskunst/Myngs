package com.eagskunst.apps.myngs.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.UUID

/**
 * Created by eagskunst in 24/7/2020.
 */
@Entity(tableName = "searches")
data class Search(
    @PrimaryKey @ColumnInfo(name = "search_id") val id: String = UUID.randomUUID().toString(),
    @ColumnInfo val sentence: String,
    @ColumnInfo(name = "started_from") val startedFrom: Int,
    @ColumnInfo val isEmptySearch: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Long = Calendar.getInstance().time.time
)
