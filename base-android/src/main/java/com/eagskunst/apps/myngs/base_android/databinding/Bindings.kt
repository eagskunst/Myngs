package com.eagskunst.apps.myngs.base_android.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.eagskunst.apps.myngs.base_android.R

/**
 * Created by eagskunst in 25/7/2020.
 */

@BindingAdapter("loadImageUrl")
fun loadImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        imageView.load(it) {
            crossfade(true)
        }
    }
}

@BindingAdapter("loadSongImage")
fun loadSongImage(imageView: ImageView, url: String?) {
    url?.let {
        imageView.load(it) {
            crossfade(true)
            placeholder(R.drawable.song_placeholder)
        }
    }
}