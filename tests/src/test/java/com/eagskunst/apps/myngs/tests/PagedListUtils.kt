package com.eagskunst.apps.myngs.tests

/**
 * Created by eagskunst in 27/7/2020.
 * Taken from this repository:
 * https://github.com/saied89/DVDPrism/blob/master/app/src/androidTest/java/com/saied/dvdprism/app/PagedListMock.kt
 */

import android.database.Cursor
import androidx.paging.DataSource
import androidx.room.RoomDatabase
import androidx.room.RoomSQLiteQuery
import androidx.room.paging.LimitOffsetDataSource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher


fun <T> createMockDataSourceFactory(itemList: List<T>, callbackDelay: Long = 2000): DataSource.Factory<Int, T> =
    object : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> = MockLimitDataSource(itemList, callbackDelay)
    }

private val mockQuery = mockk<RoomSQLiteQuery> {
    every { sql } returns ""
}

private val mockDb = mockk<RoomDatabase> {
    every { invalidationTracker } returns mockk(relaxUnitFun = true)
}

class MockLimitDataSource<T>(private val itemList: List<T>, val callbackDelay: Long) : LimitOffsetDataSource<T>(mockDb, mockQuery, false, null) {
    override fun convertRows(cursor: Cursor?): MutableList<T> = itemList.toMutableList()
    override fun countItems(): Int = itemList.count()
    override fun isInvalid(): Boolean = false
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) { /* Not implemented */ }

    override fun loadRange(startPosition: Int, loadCount: Int) =
        itemList.subList(startPosition, startPosition + loadCount).toMutableList()

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        callback.onResult(itemList, 0)
    }

}