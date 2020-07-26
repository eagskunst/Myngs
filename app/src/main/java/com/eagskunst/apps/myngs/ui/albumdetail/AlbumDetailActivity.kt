package com.eagskunst.apps.myngs.ui.albumdetail

import android.view.LayoutInflater
import androidx.lifecycle.observe
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.databinding.ActivityAlbumDetailBinding
import com.eagskunst.apps.myngs.errorWithMessage
import com.eagskunst.apps.myngs.loader
import com.eagskunst.apps.myngs.song
import com.eagskunst.apps.myngs.utils.Constants
import org.koin.android.viewmodel.ext.android.viewModel

class AlbumDetailActivity(
    override val bindingFunction: (LayoutInflater) -> ActivityAlbumDetailBinding =
        ActivityAlbumDetailBinding::inflate
) : MyngsActivity<ActivityAlbumDetailBinding>() {

    private val viewModel: AlbumDetailViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        val parcelizedAlbum =
            intent?.extras?.getParcelable<ParcelizedAlbum>(Constants.IntentKeys.PARCELIZED_ALBUM_KEY)
                ?: throw KotlinNullPointerException("This activity must receive an ParcelizedAlbum")

        with(binding.albumToolbarView) {
            albumToolbar.title = parcelizedAlbum.name
            albumHeaderView.lifecycleOwner = this@AlbumDetailActivity
            albumHeaderView.album = parcelizedAlbum
        }

        viewModel.viewState.observe(this) { state ->
            if (state.initial) return@observe

            buildRecyclerView(state)
        }

        viewModel.getAlbumById(parcelizedAlbum.id)
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
                            showAlbumImage(false)
                        }
                    }
                }
            }
        }
    }

}