package com.eagskunst.apps.myngs.ui.albumdetail

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.ViewCompat
import androidx.lifecycle.observe
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.databinding.ActivityAlbumDetailBinding
import com.eagskunst.apps.myngs.errorWithMessage
import com.eagskunst.apps.myngs.loader
import com.eagskunst.apps.myngs.myngsButton
import com.eagskunst.apps.myngs.song
import com.eagskunst.apps.myngs.ui.songplayback.ParcelableSong
import com.eagskunst.apps.myngs.ui.songplayback.SongPlaybackActivity
import com.eagskunst.apps.myngs.utils.Constants
import org.koin.android.viewmodel.ext.android.viewModel

class AlbumDetailActivity(
    override val bindingFunction: (LayoutInflater) -> ActivityAlbumDetailBinding =
        ActivityAlbumDetailBinding::inflate
) : MyngsActivity<ActivityAlbumDetailBinding>() {

    private val viewModel: AlbumDetailViewModel by viewModel()
    private var albumId = 0L

    override fun onStart() {
        super.onStart()
        binding.albumToolbarView.albumToolbar.title
        val parcelableAlbum =
            intent?.extras?.getParcelable<ParcelableAlbum>(Constants.IntentKeys.PARCELIZED_ALBUM_KEY)
                ?: throw KotlinNullPointerException("This activity must receive an ParcelizedAlbum")

        with(binding.albumToolbarView) {
            albumToolbar.setOnClickListener {
                undoFromLeftToRightAndFinish()
            }
            albumToolbar.title = parcelableAlbum.name
        }

        with(binding.albumHeaderView) {
            lifecycleOwner = this@AlbumDetailActivity
            album = parcelableAlbum
            artistNameText = "Album by ${parcelableAlbum.creatorName}"
        }

        viewModel.viewState.observe(this) { state ->
            if (state.initial) return@observe

            buildRecyclerView(state)
        }

        if (viewModel.viewState.value!!.initial) {
            viewModel.getAlbumById(parcelableAlbum.id)
        }

        albumId = parcelableAlbum.id
        binding.albumDetailRv.isNestedScrollingEnabled = false
    }

    private fun buildRecyclerView(state: AlbumDetailViewState) {
        binding.albumDetailRv.withModels {
            if (state.isLoading) {
                loader { id("loader") }
                return@withModels
            }

            when (state.error) {
                is AlbumDetailViewState.Error.Network -> {
                    errorWithMessage {
                        id("errorview")
                        errorMessage("Check your internet connection and try again")
                    }
                    myngsButton {
                        id("myngsButton")
                        text("Retry")
                        onClick { _, _, _, _ -> viewModel.getAlbumById(albumId) }
                    }
                }
                is AlbumDetailViewState.Error.EmptyAlbum -> {
                    errorWithMessage {
                        id("errorview")
                        errorMessage("Looks like this album don't have any songs. Go back and try with another song")
                    }
                }
                is AlbumDetailViewState.Error.None -> {
                    state.albumWithSongs!!.songs.forEach {
                        song {
                            id(it.id)
                            song(it)
                            albumAndCreatorText(it.creatorName)
                            showAlbumImage(false)
                            onClick { _, _, _, _ ->
                                goToSongPlaybackActivity(it)
                            }
                        }
                    }
                }
            }

            binding.albumDetailRv.requestLayout()
        }
    }

    private fun goToSongPlaybackActivity(song: Song) {
        val intent = Intent(this, SongPlaybackActivity::class.java).apply {
            putExtra(Constants.IntentKeys.PARCELIZED_SONG_KEY, ParcelableSong.fromSong(song))
        }

        startActivity(intent)
        doFromLeftToRight()
    }
}
