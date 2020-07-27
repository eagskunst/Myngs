package com.eagskunst.apps.myngs.ui.savedsearches

import android.os.Parcelable
import com.eagskunst.apps.myngs.data.entities.Search
import kotlinx.android.parcel.Parcelize

/**
 * Created by eagskunst in 26/7/2020.
 */
@Parcelize
data class ParcelableSearch(
    val id: String,
    val sentence: String
) : Parcelable {

    companion object {
        fun fromSearch(search: Search) = ParcelableSearch(
            id = search.id,
            sentence = search.sentence
        )
    }
}
