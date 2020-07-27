package com.eagskunst.apps.myngs.ui.songplayback

import android.net.Uri
import android.view.LayoutInflater
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.databinding.ActivitySongPlaybackBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


class SongPlaybackActivity(override val bindingFunction: (LayoutInflater) -> ActivitySongPlaybackBinding = ActivitySongPlaybackBinding::inflate) :
    MyngsActivity<ActivitySongPlaybackBinding>() {

    override fun onStart() {
        super.onStart()
        val player = SimpleExoPlayer.Builder(this).build()

        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, "Myngs")
            )

        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse("https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview118/v4/b0/8a/65/b08a650b-256b-fa77-6241-045695e539ea/mzaf_6860803777625206310.plus.aac.p.m4a"))

        player.prepare(videoSource)
        player.playWhenReady = true
        binding.playerView.player = player
    }


}