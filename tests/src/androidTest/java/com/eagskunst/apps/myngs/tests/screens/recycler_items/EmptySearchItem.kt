package com.eagskunst.apps.myngs.tests.screens.recycler_items

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.text.KTextView
import com.eagskunst.apps.myngs.R
import org.hamcrest.Matcher

/**
 * Created by eagskunst in 26/7/2020.
 */
class EmptySearchItem(parent: Matcher<View>): KRecyclerItem<EmptySearchItem>(parent) {
    val image = KImageView { withId(R.id.iconIv) }
    val text = KTextView { withId(R.id.infoTv) }
}