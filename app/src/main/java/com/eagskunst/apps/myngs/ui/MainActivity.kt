package com.eagskunst.apps.myngs.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eagskunst.apps.myngs.R
import com.eagskunst.apps.myngs.base.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("Mensaje de prueba")
    }
}