package com.eagskunst.apps.myngs.ui.albumdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eagskunst.apps.myng.domain.interactors.GetAlbum
import com.eagskunst.apps.myngs.base.ErrorResult
import com.eagskunst.apps.myngs.base.Success
import com.eagskunst.apps.myngs.base_android.MyngsViewModel
import kotlinx.coroutines.launch

/**
 * Created by eagskunst in 26/7/2020.
 */
class AlbumDetailViewModel(private val getAlbum: GetAlbum): MyngsViewModel() {

    private val _viewState = MutableLiveData<AlbumDetailViewState>(AlbumDetailViewState())
    val viewState = _viewState as LiveData<AlbumDetailViewState>

    fun getAlbumById(albumId: Long) {
        viewModelScope.launch {
            var newState = _viewState.value!!.copy(
                isLoading = true,
                error = AlbumDetailViewState.Error.None,
                initial = false
            )
            updateState(newState)
            val result = getAlbum(albumId)
            newState = when (result) {
                is Success -> {
                    val data = result.data
                    if (data.album.hasSongs) {
                        newState.copy(
                            albumWithSongs = result.data
                        )
                    }
                    else {
                        newState.copy(
                            error = AlbumDetailViewState.Error.EmptyAlbum
                        )
                    }
                }
                is ErrorResult -> newState.copy(
                    error = AlbumDetailViewState.Error.Network
                )
            }
            newState = newState.copy(isLoading = false)
            updateState(newState)
        }
    }

    private fun updateState(newState: AlbumDetailViewState) {
        _viewState.value = newState
    }
}