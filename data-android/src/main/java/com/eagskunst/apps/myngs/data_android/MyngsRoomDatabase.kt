package com.eagskunst.apps.myngs.data_android

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eagskunst.apps.myngs.data.MyngsDb
import com.eagskunst.apps.myngs.data.entities.Album
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.data.entities.Song

/**
 * Created by eagskunst in 25/7/2020.
 */
@Database(
    entities = [
        Song::class,
        Album::class,
        Search::class
    ],
    version = 1
)
abstract class MyngsRoomDatabase : RoomDatabase(), MyngsDb
