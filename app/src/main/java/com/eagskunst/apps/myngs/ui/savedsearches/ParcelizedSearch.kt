package com.eagskunst.apps.myngs.ui.savedsearches

import android.os.Parcelable
import com.eagskunst.apps.myngs.data.entities.Search
import kotlinx.android.parcel.Parcelize

/**
 * Created by eagskunst in 26/7/2020.
 */
@Parcelize
data class ParcelizedSearch(
    val id: String,
    val sentence: String
): Parcelable {

    companion object {
        fun fromSearch(search: Search) = ParcelizedSearch(
            id = search.id,
            sentence = search.sentence
        )
    }
}