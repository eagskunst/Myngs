package com.eagskunst.apps.myngs.ui.albumdetail

import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.observe
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.databinding.ActivityAlbumDetailBinding
import com.eagskunst.apps.myngs.errorWithMessage
import com.eagskunst.apps.myngs.loader
import com.eagskunst.apps.myngs.myngsButton
import com.eagskunst.apps.myngs.song
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
        val parcelizedAlbum =
            intent?.extras?.getParcelable<ParcelableAlbum>(Constants.IntentKeys.PARCELIZED_ALBUM_KEY)
                ?: throw KotlinNullPointerException("This activity must receive an ParcelizedAlbum")

        with(binding.albumToolbarView) {
            albumToolbar.setOnClickListener { finish() }
            albumToolbar.title = parcelizedAlbum.name
        }

        with(binding.albumHeaderView) {
            lifecycleOwner = this@AlbumDetailActivity
            album = parcelizedAlbum
            artistNameText = "Album by ${parcelizedAlbum.creatorName}"
        }

        viewModel.viewState.observe(this) { state ->
            if (state.initial) return@observe

            buildRecyclerView(state)
        }

        viewModel.getAlbumById(parcelizedAlbum.id)
        albumId = parcelizedAlbum.id
    }

    private fun buildRecyclerView(state: AlbumDetailViewState) {
        binding.albumDetailRv.isNestedScrollingEnabled = false
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
                                startActivity(Intent(this@AlbumDetailActivity, SongPlaybackActivity::class.java))
                            }
                        }
                    }
                }

            }

            binding.albumDetailRv.requestLayout()
        }
    }

}