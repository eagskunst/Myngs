package com.eagskunst.apps.myngs.ui.savedsearches

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.observe
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.databinding.ActivitySavedSearchesBinding
import com.eagskunst.apps.myngs.errorWithMessage
import com.eagskunst.apps.myngs.loader
import com.eagskunst.apps.myngs.myngsButton
import com.eagskunst.apps.myngs.searchText
import com.eagskunst.apps.myngs.utils.Constants
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by eagskunst in 26/7/2020.
 */
class SavedSearchesActivity(
    override val bindingFunction: (LayoutInflater) -> ActivitySavedSearchesBinding = ActivitySavedSearchesBinding::inflate
) : MyngsActivity<ActivitySavedSearchesBinding>() {

    private val viewModel: SavedSearchesViewModel by viewModel()

    override fun onStart() {
        super.onStart()

        binding.headerView.savedSearchesToolbar.setNavigationOnClickListener {
            goBack(Activity.RESULT_CANCELED)
        }

        viewModel.viewState.observe(this) { state ->
            buildRecyclerView(state)
        }

        viewModel.getSavedSearches()
    }

    private fun buildRecyclerView(state: SavedSearchesViewState) {
        binding.searchesRv.withModels {
            when {
                state.isLoading -> loader { id("loader") }
                state.error == SavedSearchesViewState.Error.NoSearchesError -> {
                    errorWithMessage {
                        id("errormessage")
                        errorMessage("You have not done a search that returned songs.")
                    }
                    myngsButton {
                        id("button")
                        text("Make a search")
                        onClick { _, _, _, _ -> goBack(Activity.RESULT_CANCELED) }
                    }
                }
                else -> {
                    state.searches!!.forEach { search ->
                        searchText {
                            id(search.id)
                            text(search.sentence)
                            onClick { _, _, _, _ ->
                                goBack(Activity.RESULT_OK, search)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun goBack(resultCode: Int, search: Search? = null) {
        val intent = Intent().apply {
            search?.let {
                putExtra(
                    Constants.IntentKeys.PARCELIZED_SEARCH_KEY,
                    ParcelizedSearch.fromSearch(search)
                )
            }
        }
        setResult(resultCode, intent)
        finish()
    }

}