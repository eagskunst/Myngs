package com.eagskunst.apps.myngs.ui.home

import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.base_android.UtilityTextWatcher
import com.eagskunst.apps.myngs.base_android.hideKeyboard
import com.eagskunst.apps.myngs.base_android.showToast
import com.eagskunst.apps.myngs.data.entities.albumAndCreatorNameString
import com.eagskunst.apps.myngs.databinding.ActivityHomeBinding
import com.eagskunst.apps.myngs.emptySearch
import com.eagskunst.apps.myngs.loader
import com.eagskunst.apps.myngs.song
import kotlinx.coroutines.delay
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity(override val bindingFunction: (LayoutInflater) -> ActivityHomeBinding = ActivityHomeBinding::inflate) :
    MyngsActivity<ActivityHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        viewModel.viewState.observe(this) { state ->
            if (state.initial) return@observe
            
            buildRecyclerView(state)
        }

        binding.homeHeader.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val input = binding.homeHeader.searchInput.text.toString()
                if (input.isNotEmpty()) {
                    viewModel.searchForTerm(input)
                    hideKeyboard()
                }
                else {
                    showToast("Write something first!")
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun buildRecyclerView(state: HomeViewState) {
        binding.songsRv.withModels {
            when {
                state.isLoading -> {
                    loader { id("loader") }
                }
                state.error == HomeViewState.Error.None -> {
                    state.songs!!.forEach { song ->
                        song {
                            id(song.id)
                            song(song)
                            albumAndCreatorText(song.albumAndCreatorNameString())
                            onClick { _, _, _, _ ->
                                showSnackbar(R.color.colorAccent, "Clicked song ${song.name}")
                            }
                        }
                    }
                }
                else -> {
                    emptySearch { id("emptysearch") }
                }
            }
        }
    }

}