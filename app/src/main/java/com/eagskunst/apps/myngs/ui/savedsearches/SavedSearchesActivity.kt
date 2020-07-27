package com.eagskunst.apps.myngs.ui.savedsearches

import android.view.LayoutInflater
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.databinding.ActivitySavedSearchesBinding
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
    }

}