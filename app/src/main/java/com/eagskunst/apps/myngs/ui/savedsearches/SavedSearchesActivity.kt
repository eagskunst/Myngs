package com.eagskunst.apps.myngs.ui.savedsearches

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.TooltipCompat
import androidx.lifecycle.observe
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.data.entities.Search
import com.eagskunst.apps.myngs.databinding.ActivitySavedSearchesBinding
import com.eagskunst.apps.myngs.errorWithMessage
import com.eagskunst.apps.myngs.loader
import com.eagskunst.apps.myngs.myngsButton
import com.eagskunst.apps.myngs.searchItem
import com.eagskunst.apps.myngs.utils.Constants
import kotlinx.android.synthetic.main.activity_saved_searches.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by eagskunst in 26/7/2020.
 */
class SavedSearchesActivity(
    override val bindingFunction: (LayoutInflater) -> ActivitySavedSearchesBinding = ActivitySavedSearchesBinding::inflate
) : MyngsActivity<ActivitySavedSearchesBinding>() {

    private val viewModel: SavedSearchesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.headerView.savedSearchesToolbar.setNavigationOnClickListener {
            goBack(Activity.RESULT_CANCELED)
        }

        viewModel.viewState.observe(this) { state ->
            buildRecyclerView(state)
        }

        viewModel.getSavedSearches()
        TooltipCompat.setTooltipText(
            binding.clearSearchesFab,
            getString(R.string.clear_searches_tooltip)
        )
        binding.clearSearchesFab.setOnClickListener {
            viewModel.deleteAllSearches()
        }
    }

    private fun buildRecyclerView(state: SavedSearchesViewState) {
        binding.searchesRv.withModels {
            when {
                state.isLoading -> loader {
                    id("loader")
                    clearSearchesFab.hide()
                }
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
                    clearSearchesFab.hide()
                }
                else -> {
                    state.searches!!.forEach { search ->
                        searchItem {
                            id(search.id)
                            text(search.sentence)
                            onClick { _, _, _, _ ->
                                goBack(Activity.RESULT_OK, search)
                            }
                            onDeleteClick { _, _, _, _ ->
                                viewModel.deleteSearch(search)
                            }
                        }
                    }
                    binding.clearSearchesFab.show()
                }
            }
        }
    }

    override fun onBackPressed() {
        goBack(Activity.RESULT_CANCELED)
    }

    private fun goBack(resultCode: Int, search: Search? = null) {
        val intent = Intent().apply {
            search?.let {
                putExtra(
                    Constants.IntentKeys.PARCELIZED_SEARCH_KEY,
                    ParcelableSearch.fromSearch(search)
                )
            }
        }
        setResult(resultCode, intent)
        finishAndSlideDown()
    }
}
