package com.eagskunst.apps.myngs.ui.home

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.eagskunst.apps.myngs.ErrorWithMessageBindingModel_
import com.eagskunst.apps.myngs.ImageWithMessageBindingModel_
import com.eagskunst.apps.myngs.LoaderBindingModel_
import com.eagskunst.apps.myngs.MyngsButtonBindingModel_
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.SongBindingModel_
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.entities.albumAndCreatorNameString
import com.eagskunst.apps.myngs.errorWithMessage
import com.eagskunst.apps.myngs.imageWithMessage
import com.eagskunst.apps.myngs.loader
import com.eagskunst.apps.myngs.myngsButton
import com.eagskunst.apps.myngs.song

/**
 * Created by eagskunst in 27/7/2020.
 */
class HomePagedController(val callbacks: Callbacks) : PagedListEpoxyController<Song>() {

    var viewState = HomeViewState(initial = true)
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildItemModel(currentPosition: Int, item: Song?): EpoxyModel<*> {
        return SongBindingModel_()
            .id(item?.id)
            .song(item)
            .showAlbumImage(true)
            .albumAndCreatorText(item?.albumAndCreatorNameString())
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
        when {

            viewState.initial -> {
                imageWithMessage {
                    id("initalstate")
                    infoMessage("Don't know where to start? Go to your saved searches")
                }
                myngsButton {
                    id("myngsButton")
                    text("Select a saved search")
                    onClick { _, _, _, _ ->
                        callbacks.onRetryButtonClick()
                    }
                }
            }

            viewState.isLoading -> {
                loader { id("loader") }
            }

            viewState.error == HomeViewState.Error.EmptySearch -> {
                errorWithMessage {
                    id("emptysearch")
                    errorMessage(callbacks.errorMessage())
                }
            }

            viewState.error == HomeViewState.Error.SearchFailed -> {
                errorWithMessage {
                    id("emptysearch")
                    errorMessage("Oh no. Looks like you have connection issues :(")
                }
                myngsButton {
                    id("myngsButton")
                    text("Select a saved search")
                    onClick { _, _, _, _ ->
                        callbacks.onRetryButtonClick()
                    }
                }
            }
        }
    }

    interface Callbacks {
        fun onInitialButtonClick()
        fun onRetryButtonClick()
        fun onSongClicked(song: Song, view: View)
        fun errorMessage(): String
    }
}