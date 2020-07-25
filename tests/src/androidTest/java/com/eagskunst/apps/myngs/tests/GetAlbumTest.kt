package com.eagskunst.apps.myngs.tests

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.eagskunst.apps.myng.domain.interactors.GetAlbum
import com.eagskunst.apps.myngs.base.ErrorResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.data.MyngsDb
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.entities.relationships.AlbumWithSongs
import com.eagskunst.apps.myngs.data.responses.TunesQueryResponse
import com.eagskunst.apps.myngs.data.services.AlbumService
import com.eagskunst.apps.myngs.data_android.KoinModulesImpl
import com.eagskunst.apps.myngs.tests.robots.ConditionRobot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.koin.core.inject
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import java.net.SocketTimeoutException

/**
 * Created by eagskunst in 25/7/2020.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@ExperimentalCoroutinesApi
class GetAlbumTest: KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val expectedException = ExpectedException.none()

    val testDbModule = TestDatabaseModule()
    val dispatchersModule = DispatchersModule()

    private val database: MyngsDb by inject()
    private val getAlbum: GetAlbum by inject()
    private val albumService: AlbumService = mockk()

    @get:Rule
    val koinRule = KoinTestRule.create {
        val context = ApplicationProvider.getApplicationContext<Context>()
        printLogger()
        modules(
            KoinModulesImpl.allButServicesDbAndBase() +
                testDbModule(context) + dispatchersModule() +
                module {
                    single(override = true) {
                        albumService
                    }
                }
        )
    }

    @Test
    fun getAlbum_ReturnSuccess_AssertDbInsertions_VerifyServiceWasCalled() {
        val albumId = 64845L
        coEvery { albumService.getAlbumById(id = albumId) } returns TunesQueryResponse(
            results = SampleData.sampleResponseWithAlbumFirst(albumId),
            resultCount = 20
        )

        runBlocking {
            val res = getAlbum(albumId)
            assert(res is Success)
            val resData = res.getOrThrow()
            assertThat(resData.songs.size, `is`(19))
            assertThat(resData.album.id, `is`(albumId))
            coVerify(exactly = 1) { albumService.getAlbumById(albumId) }
            val albumWithSongs = awaitForInsertion(albumId)
            assertThat(albumWithSongs.songs.first().name, `is`(resData.songs.first().name))
            assertThat(albumWithSongs.songs.size, `is`(resData.songs.size))
            assertThat(albumWithSongs.album.name, `is`(resData.album.name))
        }
    }

    private suspend fun awaitForInsertion(albumId: Long): AlbumWithSongs {
        ConditionRobot().waitUntil(maxTries = 25) { database.albumDao().getAlbumWithSongs(albumId) != null }
        return database.albumDao().getAlbumWithSongs(albumId)!!
    }

    @Test
    fun addSongWithSearchId_GetAlbumByThatId_AssertTheSongDontLosesSearchId() {
        val albumId = 55457L
        val searchId = addSongAndSearch(albumId)
        coEvery { albumService.getAlbumById(id = albumId) } returns TunesQueryResponse(
            results = SampleData.sampleResponseWithAlbumFirst(albumId),
            resultCount = 20
        )

        runBlocking {
            getAlbum(albumId)
            val albumsFromDb = awaitForInsertion(albumId)
            val song = albumsFromDb.songs.firstOrNull { it.searchId == searchId }
            assert(song != null)
        }
    }

    private fun addSongAndSearch(albumId: Long) = runBlocking {
        return@runBlocking with(database) {
            val search = Search(sentence = "something", isEmptySearch = false, startedFrom = 0)
            searchDao().addSearch(search)
            val song = Song(
                id = 0.toLong(),
                name = "song0",
                creatorName = "creator0",
                previewUrl = null,
                searchId = search.id,
                albumId = albumId
            )
            songDao().addSong(song)
            assert(songDao().getSongById(0.toLong()) != null)
            search.id
        }
    }

    @Test
    fun afterGetAlbumSuccess_RepeatQuery_VerifyServiceWasCalledJustOnce() {
        val albumId = 21654L
        coEvery { albumService.getAlbumById(id = albumId) } returns TunesQueryResponse(
            results = SampleData.sampleResponseWithAlbumFirst(albumId),
            resultCount = 20
        )

        runBlocking {
            getAlbum(albumId)
            awaitForInsertion(albumId)
            getAlbum(albumId)
            coVerify(exactly = 1) { albumService.getAlbumById(id = albumId) }
        }
    }

    @Test
    fun afterGetAlbumError_RepeatQuery_VerifyServiceWasCalledTwice() {
        val albumId = 564683L
        coEvery { albumService.getAlbumById(id = albumId) } throws SocketTimeoutException("Timeout") andThen(TunesQueryResponse(
            results = SampleData.sampleResponseWithAlbumFirst(albumId),
            resultCount = 20
        ))

        runBlocking {
            val result = getAlbum(albumId)
            assert(result is ErrorResult)
            getAlbum(albumId)
            coVerify(exactly = 2) { albumService.getAlbumById(id = albumId) }
        }
    }

}