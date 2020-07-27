package com.eagskunst.apps.myngs.ui.songplayback

import android.os.Parcelable
import com.eagskunst.apps.myngs.data.entities.Song
import kotlinx.android.parcel.Parcelize

/**
 * Created by eagskunst in 26/7/2020.
 */
@Parcelize
data class ParcelableSong(
    val name: String,
    val albumName: String,
    val artistName: String,
    val artworkUrl: String?,
    val previewUrl: String?
) : Parcelable {

    companion object {
        fun fromSong(song: Song) = with(song) {
            ParcelableSong(
                name = name,
                albumName = albumName ?: "",
                previewUrl = previewUrl,
                artistName = creatorName,
                artworkUrl = artwork?.replace("100", "500")
            )
        }
    }
}
