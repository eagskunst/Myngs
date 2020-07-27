package com.eagskunst.apps.myngs.ui.songplayback

import android.view.LayoutInflater
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.databinding.ActivitySongPlaybackBinding
import com.eagskunst.apps.myngs.utils.Constants
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class SongPlaybackActivity(override val bindingFunction: (LayoutInflater) -> ActivitySongPlaybackBinding = ActivitySongPlaybackBinding::inflate) :
    MyngsActivity<ActivitySongPlaybackBinding>() {

    private val dsFactory by inject<DataSource.Factory>()
    private val player: SimpleExoPlayer by inject { parametersOf(this) }

    override fun onStart() {
        super.onStart()
        val parcelableSong = intent.getParcelableExtra<ParcelableSong>(Constants.IntentKeys.PARCELIZED_SONG_KEY) ?: return

        lifecycleScope.launchWhenResumed {
            binding.playbackToolbar.setNavigationOnClickListener { finish() }
            binding.song = parcelableSong
        }

        parcelableSong.previewUrl?.let {
            val mediaSource = createMediaSource(it)
            player.prepare(mediaSource)
        }
        if (parcelableSong.previewUrl == null) {
            showSnackbar(R.color.colorAccent, "There's a problem with the preview and the media can't be played.", Snackbar.LENGTH_LONG)
        }

        binding.playerView.player = player

    }

    override fun onResume() {
        super.onResume()
        player.playWhenReady = true
        binding.playerView.showTimeoutMs = 0
    }

    override fun onPause() {
        super.onPause()
        player.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    private fun createMediaSource(mediaUrl: String): MediaSource {
        return ProgressiveMediaSource.Factory(dsFactory)
            .createMediaSource(mediaUrl?.toUri())
    }


}