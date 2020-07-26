package com.eagskunst.apps.myngs.tests.screens.recycler_items

import android.view.View
import com.agoda.kakao.progress.KProgressBar
import com.agoda.kakao.recycler.KRecyclerItem
import com.eagskunst.apps.myngs.R
import org.hamcrest.Matcher

/**
 * Created by eagskunst in 26/7/2020.
 */
class ProgressBarItem(parent: Matcher<View>): KRecyclerItem<ProgressBarItem>(parent) {
    val progressBar = KProgressBar { withId(R.id.progressBar) }
}