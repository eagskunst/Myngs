package com.eagskunst.apps.myngs.ui.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.TooltipCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.observe
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.base.Timber
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.base_android.hideKeyboard
import com.eagskunst.apps.myngs.base_android.showToast
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.databinding.ActivityHomeBinding
import com.eagskunst.apps.myngs.ui.albumdetail.AlbumDetailActivity
import com.eagskunst.apps.myngs.ui.albumdetail.ParcelableAlbum
import com.eagskunst.apps.myngs.ui.savedsearches.ParcelableSearch
import com.eagskunst.apps.myngs.ui.savedsearches.SavedSearchesActivity
import com.eagskunst.apps.myngs.utils.Constants
import org.koin.android.viewmodel.ext.android.viewModel

typealias TransitionPair = androidx.core.util.Pair<View, String>

class HomeActivity(override val bindingFunction: (LayoutInflater) -> ActivityHomeBinding = ActivityHomeBinding::inflate) :
    MyngsActivity<ActivityHomeBinding>(), HomePagedController.Callbacks {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var errorMessage: String

    private val savedSearches =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { intent ->
            if (intent.resultCode == Activity.RESULT_OK) {
                val selectedSearch =
                    intent.data?.getParcelableExtra<ParcelableSearch>(Constants.IntentKeys.PARCELIZED_SEARCH_KEY)
                binding.homeHeader.searchInput.setText(selectedSearch?.sentence)
                executeSearch()
            }
        }

    override fun onStart() {
        super.onStart()
        errorMessage = getString(R.string.empty_search_text)
        val controller = HomePagedController(this)
        viewModel.viewState.observe(this) { state ->
            controller.viewState = state
            if (state.songs != null) {
                controller.submitList(state.songs)
            }
            if (!state.initial) {
                binding.recentSearchesFab.show()
            }
        }
        binding.songsRv.setController(controller)

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

    override fun errorMessage(): String {
        return errorMessage
    }

    override fun onInitialButtonClick() {
        goToSavedSearches()
    }

    override fun onSavedSearchesClick() {
        goToSavedSearches()
    }

    override fun onSongClicked(song: Song, view: View) {
        goToAlbumDetail(song, view)
    }

    private fun goToAlbumDetail(song: Song, view: View) {
        val iv = view.findViewById<ImageView>(R.id.albumArtworkIv)
        val tv = view.findViewById<TextView>(R.id.albumAndCreatorTv)
        val pairs = listOf<TransitionPair>(
            androidx.core.util.Pair(
                iv,
                Constants.Transitions.ALBUM_IMAGE_TRANSITION_NAME
            )
        )

        val intent = Intent(this, AlbumDetailActivity::class.java).apply {
            putExtra(Constants.IntentKeys.PARCELIZED_ALBUM_KEY, ParcelableAlbum.fromSong(song))
        }

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs[0])
        startActivity(intent, options.toBundle())
    }

    private fun goToSavedSearches() {
        savedSearches.launch(Intent(this, SavedSearchesActivity::class.java))
        slideUpAndStay()
    }
}
