package com.eagskunst.apps.myngs.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eagskunst.apps.myng.domain.interactors.GetAlbum
import com.eagskunst.apps.myngs.base.ErrorResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.base.errors.EmptyAlbumException
import com.eagskunst.apps.myngs.data.mapper.TunesQueryToAlbumWithSongsMapper
import com.eagskunst.apps.myngs.data.mapper.TunesQueryToSongsMapper
import com.eagskunst.apps.myngs.data.responses.TunesQueryResponse
import com.eagskunst.apps.myngs.ui.albumdetail.AlbumDetailViewModel
import com.eagskunst.apps.myngs.ui.albumdetail.AlbumDetailViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import java.net.SocketTimeoutException

/**
 * Created by eagskunst in 26/7/2020.
 */
@ExperimentalCoroutinesApi
class AlbumDetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule = TestCoroutineRule()

    private val getAlbum: GetAlbum = mockk()
    private val viewModel = AlbumDetailViewModel(getAlbum)
    val mapper = TunesQueryToAlbumWithSongsMapper(TunesQueryToSongsMapper())
    private val albumId = 123456L

    @Test
    fun assertInitialState() {
        assertThat(
            viewModel.viewState.getOrAwaitValue().initial,
            `is`(true)
        )
    }

    @Test
    fun assertLoadingIsEmitted_AndInitialIsFalse() {
        coEvery { getAlbum(albumId) } coAnswers {
            delay(2000)
            Success(
                mapper.map(
                    TunesQueryResponse(20, SampleData.sampleResponseWithAlbumFirst(albumId))
                )
            )
        }

        viewModel.getAlbumById(albumId)
        assertThat(
            viewModel.viewState.getOrAwaitValue().initial,
            `is`(false)
        )
        assertThat(
            viewModel.viewState.getOrAwaitValue().isLoading,
            `is`(true)
        )
        coroutineRule.runBlockingTest {
            delay(2500)
        }
    }

    @Test
    fun whenSuccessResult_AssertListIsEmitted() {
        coEvery { getAlbum(albumId) } coAnswers {
            Success(
                mapper.map(
                    TunesQueryResponse(20, SampleData.sampleResponseWithAlbumFirst(albumId))
                )
            )
        }
        viewModel.getAlbumById(albumId)
        val newState = viewModel.viewState.getOrAwaitValue()
        assertThat(newState.albumWithSongs!!.album.id, `is`(albumId))
        assertThat(newState.albumWithSongs!!.album.name, `is`("mocked_album"))
        assertThat(newState.albumWithSongs!!.album.hasSongs, `is`(true))
        assertThat(newState.albumWithSongs!!.songs.size, `is`(19))
    }

    @Test
    fun whenErrorResult_AssertErrorIsEmitted() {
        coEvery { getAlbum(albumId) } coAnswers {
            ErrorResult(
                throwable = SocketTimeoutException("timeout exception")
            )
        }
        viewModel.getAlbumById(albumId)
        val newState = viewModel.viewState.getOrAwaitValue()
        assert(newState.error is AlbumDetailViewState.Error.Network)
    }

    @Test
    fun whenSuccessResultOfEmptyAlbum_AssertEmptyAlbumErrorIsEmitted() {
        coEvery { getAlbum(albumId) } coAnswers {
            Success(
                mapper.map(
                    TunesQueryResponse(
                        resultCount = 1,
                        results = listOf(SampleData.sampleResponseWithAlbumFirst(albumId).first())
                    )
                )
            )
        }
        viewModel.getAlbumById(albumId)
        val newState = viewModel.viewState.getOrAwaitValue()
        assert(newState.error is AlbumDetailViewState.Error.EmptyAlbum)
    }

}