package com.eagskunst.apps.myngs.tests

import android.content.Context
import org.koin.dsl.module
import androidx.room.Room
import com.eagskunst.apps.myngs.data.MyngsDb
import com.eagskunst.apps.myngs.data_android.MyngsRoomDatabase

/**
 * Created by eagskunst in 25/7/2020.
 */
class TestDatabaseModule {

    operator fun invoke(context: Context) = module {
        single<MyngsDb> {
            Room.inMemoryDatabaseBuilder(
                context,
                MyngsRoomDatabase::class.java
            ).build()
        }
    }
}