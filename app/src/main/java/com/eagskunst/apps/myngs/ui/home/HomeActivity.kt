package com.eagskunst.apps.myngs.ui.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.TooltipCompat
import androidx.lifecycle.observe
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.base_android.hideKeyboard
import com.eagskunst.apps.myngs.base_android.showToast
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.entities.albumAndCreatorNameString
import com.eagskunst.apps.myngs.databinding.ActivityHomeBinding
import com.eagskunst.apps.myngs.errorWithMessage
import com.eagskunst.apps.myngs.imageWithMessage
import com.eagskunst.apps.myngs.loader
import com.eagskunst.apps.myngs.myngsButton
import com.eagskunst.apps.myngs.song
import com.eagskunst.apps.myngs.ui.albumdetail.AlbumDetailActivity
import com.eagskunst.apps.myngs.ui.albumdetail.ParcelizedAlbum
import com.eagskunst.apps.myngs.ui.savedsearches.ParcelizedSearch
import com.eagskunst.apps.myngs.ui.savedsearches.SavedSearchesActivity
import com.eagskunst.apps.myngs.utils.Constants
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity(override val bindingFunction: (LayoutInflater) -> ActivityHomeBinding = ActivityHomeBinding::inflate) :
    MyngsActivity<ActivityHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var errorMessage: String

    private val savedSearches =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { intent ->
            if (intent.resultCode == Activity.RESULT_OK) {
                val selectedSearch =
                    intent.data?.getParcelableExtra<ParcelizedSearch>(Constants.IntentKeys.PARCELIZED_SEARCH_KEY)
                binding.homeHeader.searchInput.setText(selectedSearch?.sentence)
                executeSearch()
            }
        }

    override fun onStart() {
        super.onStart()
        errorMessage = getString(R.string.empty_search_text)

        viewModel.viewState.observe(this) { state ->
            buildRecyclerView(state)
            if (!state.initial) {
                binding.recentSearchesFab.show()
            }
        }

        binding.homeHeader.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                executeSearch()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.recentSearchesFab.setOnClickListener {
            goToSavedSearches()
        }

        binding.recentSearchesFab.hide()

        TooltipCompat.setTooltipText(binding.recentSearchesFab, "See recent searches")
    }

    private fun executeSearch() {
        val input = binding.homeHeader.searchInput.text.toString()
        if (input.isNotEmpty()) {
            viewModel.searchForTerm(input)
            hideKeyboard()
        } else {
            showToast("Write something first!")
        }
    }

    private fun buildRecyclerView(state: HomeViewState) {
        binding.songsRv.withModels {
            when {

                state.initial -> {
                    imageWithMessage {
                        id("initalstate")
                        infoMessage("Don't know where to start? Go to your saved searches")
                    }
                    myngsButton {
                        id("myngsButton")
                        text("Select a saved search")
                        onClick { _, _, _, _ ->
                            goToSavedSearches()
                        }
                    }
                }

                state.isLoading -> { loader { id("loader") } }

                state.error == HomeViewState.Error.None -> {
                    state.songs!!.forEach { song ->
                        song {
                            id(song.id)
                            song(song)
                            albumAndCreatorText(song.albumAndCreatorNameString())
                            showAlbumImage(true)
                            onClick { _, _, _, _ ->
                                goToAlbumDetail(song)
                            }
                        }
                    }
                }

                state.error == HomeViewState.Error.EmptySearch -> {
                    errorWithMessage {
                        id("emptysearch")
                        errorMessage(errorMessage)
                    }
                }

                state.error == HomeViewState.Error.SearchFailed -> {
                    errorWithMessage {
                        id("emptysearch")
                        errorMessage("Oh no. Looks like you have connection issues :(")
                    }
                    myngsButton {
                        id("myngsButton")
                        text("Select a saved search")
                        onClick { _, _, _, _ ->
                            goToSavedSearches()
                        }
                    }
                }
            }
        }
    }

    private fun goToAlbumDetail(song: Song) {
        val intent = Intent(this, AlbumDetailActivity::class.java).apply {
            putExtra(Constants.IntentKeys.PARCELIZED_ALBUM_KEY, ParcelizedAlbum.fromSong(song))
        }
        startActivity(intent)
    }

    private fun goToSavedSearches() {
        savedSearches.launch(Intent(this, SavedSearchesActivity::class.java))
    }

}