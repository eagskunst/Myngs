package com.eagskunst.apps.myngs.ui.albumdetail

import android.os.Parcelable
import com.eagskunst.apps.myngs.data.entities.Song
import kotlinx.android.parcel.Parcelize

/**
 * Created by eagskunst in 26/7/2020.
 */
@Parcelize
data class ParcelizedAlbum(
    val id: Long,
    val name: String,
    val creatorName: String,
    val artwork: String?
) : Parcelable {

    companion object {
        fun fromSong(song: Song) = with(song) {
            ParcelizedAlbum(
                id = albumId,
                creatorName = creatorName,
                name = albumName ?: "",
                artwork = artwork
            )
        }
    }
}