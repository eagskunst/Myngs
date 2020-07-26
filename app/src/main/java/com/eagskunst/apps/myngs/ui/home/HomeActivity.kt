package com.eagskunst.apps.myngs.ui.home

import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.base_android.MyngsActivity
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

        lifecycleScope.launchWhenStarted {
            delay(2000)
            viewModel.searchForTerm("in utero")
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