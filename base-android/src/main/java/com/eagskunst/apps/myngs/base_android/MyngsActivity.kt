package com.eagskunst.apps.myngs.base_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Created by eagskunst in 25/7/2020.
 */
abstract class MyngsActivity<B: ViewBinding>: AppCompatActivity() {

    abstract val layoutRes: Int
    abstract val bindingFunction:(Int) -> B
    private lateinit var _binding: B
    protected val binding: B
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFunction(layoutRes)
        setContentView(binding.root)
    }

    protected fun showSnackbar(color: Int, message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        showSnackbar(color, getString(message), duration)
    }

    protected fun showSnackbar(color: Int, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(binding.root, message, duration).show()
    }

}