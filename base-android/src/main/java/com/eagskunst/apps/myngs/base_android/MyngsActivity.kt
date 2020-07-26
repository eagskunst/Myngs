package com.eagskunst.apps.myngs.base_android

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Created by eagskunst in 25/7/2020.
 */
abstract class MyngsActivity<B: ViewBinding>: AppCompatActivity() {

    abstract val bindingFunction:(LayoutInflater) -> B
    private lateinit var _binding: B
    protected val binding: B
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFunction(layoutInflater)
        setContentView(binding.root)
    }

    protected fun showSnackbar(color: Int, message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        showSnackbar(color, getString(message), duration)
    }

    protected fun showSnackbar(color: Int, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(binding.root, message, duration).run {
            setBackgroundTint(color)
            setActionTextColor(ContextCompat.getColor(this@MyngsActivity, R.color.color_default_white))
            show()
        }
    }

}