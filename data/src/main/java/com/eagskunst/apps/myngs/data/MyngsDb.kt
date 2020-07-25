package com.eagskunst.apps.myngs.data

import com.eagskunst.apps.myngs.data.daos.AlbumDao
import com.eagskunst.apps.myngs.data.daos.SearchDao
import com.eagskunst.apps.myngs.data.daos.SongDao

/**
 * Created by eagskunst in 24/7/2020.
 */

interface MyngsDb {
    fun searchDao(): SearchDao
    fun albumDao(): AlbumDao
    fun songDao(): SongDao
}