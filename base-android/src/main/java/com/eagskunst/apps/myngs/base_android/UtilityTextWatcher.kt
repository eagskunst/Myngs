package com.eagskunst.apps.myngs.base_android

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by eagskunst in 26/7/2020.
 */
open class UtilityTextWatcher(val doAfterTextChanged:(Editable?) -> Unit): TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        doAfterTextChanged(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}