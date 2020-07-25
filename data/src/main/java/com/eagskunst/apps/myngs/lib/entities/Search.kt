package com.eagskunst.apps.myngs.lib.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by eagskunst in 24/7/2020.
 */
@Entity
data class Search(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val sentence: String,
    @ColumnInfo val stopped_at: Int
)