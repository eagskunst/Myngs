package com.eagskunst.apps.myngs.app.di

import com.eagskunst.apps.myngs.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}