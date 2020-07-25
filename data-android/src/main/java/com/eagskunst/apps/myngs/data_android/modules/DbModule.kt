package com.eagskunst.apps.myngs.data_android.modules

import androidx.room.Room
import androidx.room.RoomDatabase
import com.eagskunst.apps.myngs.data.MyngsDb
import com.eagskunst.apps.myngs.data_android.MyngsRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */

val dbModule = module {
    single<MyngsDb> {
        Room.databaseBuilder(
            androidContext(),
            MyngsRoomDatabase::class.java,
            "MyngsDatabase"
        ).build()
    }
    factory { get<MyngsDb>().albumDao() }
    factory { get<MyngsDb>().searchDao() }
    factory { get<MyngsDb>().songDao() }
}