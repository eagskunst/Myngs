package com.eagskunst.apps.myngs.ui.albumdetail

import android.os.Bundle
import android.view.LayoutInflater
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.base_android.MyngsActivity
import com.eagskunst.apps.myngs.databinding.ActivityAlbumDetailBinding

class AlbumDetailActivity(
    override val bindingFunction: (LayoutInflater) -> ActivityAlbumDetailBinding =
        ActivityAlbumDetailBinding::inflate
) : MyngsActivity<ActivityAlbumDetailBinding>() {

}