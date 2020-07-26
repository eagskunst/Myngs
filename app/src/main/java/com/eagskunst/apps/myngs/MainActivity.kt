package com.eagskunst.apps.myngs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eagskunst.apps.myng.domain.interactors.SearchTerm
import com.eagskunst.apps.myngs.base.Timber
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val terms: SearchTerm by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("Mensaje de prueba")
        GlobalScope.launch {
            val result = terms.searchSentenceForSongs("in utero")
            Timber.d("Result data: ${result.get()}")
        }
    }
}