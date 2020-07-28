package com.eagskunst.apps.myngs.ui.home

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.eagskunst.apps.myngs.SongBindingModel_
import com.eagskunst.apps.myngs.base.Timber
import com.eagskunst.apps.myngs.data.entities.Song
import com.eagskunst.apps.myngs.data.entities.albumAndCreatorNameString
import com.eagskunst.apps.myngs.errorWithMessage
import com.eagskunst.apps.myngs.imageWithMessage
import com.eagskunst.apps.myngs.loader
import com.eagskunst.apps.myngs.myngsButton

/**
 * Created by eagskunst in 27/7/2020.
 */
class HomePagedController(private val callbacks: Callbacks) : PagedListEpoxyController<Song>() {

    private val ERROR_WITH_MESSAGE_VIEW = "errorview"

    var viewState = HomeViewState(initial = true)
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildItemModel(currentPosition: Int, item: Song?): EpoxyModel<*> {
        return if (item != null) {
            SongBindingModel_()
                .id(item.id)
                .song(item)
                .showAlbumImage(true)
                .albumAndCreatorText(item.albumAndCreatorNameString())
                .onClick { _, parentView, _, _ ->
                    callbacks.onSongClicked(item, parentView.dataBinding.root)
                }
        } else SongBindingModel_()
            .id(currentPosition)
            .showAlbumImage(true)
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
                        callbacks.onSavedSearchesClick()
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
                    id(ERROR_WITH_MESSAGE_VIEW)
                    errorMessage("Oh no. Looks like you have connection issues :(")
                }
                myngsButton {
                    id("myngsButton")
                    text("Select a saved search")
                    onClick { _, _, _, _ ->
                        callbacks.onSavedSearchesClick()
                    }
                }
            }

            viewState.error == HomeViewState.Error.NoMoreItems -> {
                errorWithMessage {
                    id(ERROR_WITH_MESSAGE_VIEW)
                    errorMessage("You reached the end of the list :)`")
                }
            }
        }
    }

    override fun onExceptionSwallowed(exception: RuntimeException) {
        super.onExceptionSwallowed(exception)
        Timber.d("Exception: $exception")
    }

    interface Callbacks {
        fun onInitialButtonClick()
        fun onSavedSearchesClick()
        fun onSongClicked(song: Song, view: View)
        fun errorMessage(): String
    }
}