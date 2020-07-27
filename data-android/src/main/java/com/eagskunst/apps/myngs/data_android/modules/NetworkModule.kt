package com.eagskunst.apps.myngs.data_android.modules

import java.io.File
import java.util.concurrent.TimeUnit
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by eagskunst in 25/7/2020.
 */

val networkModule = module {
    factory {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    factory { File(androidContext().cacheDir, "okhttp_cache") }

    factory { Cache(get(), 10 * 1000 * 1000) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .readTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(90, TimeUnit.SECONDS)
            .cache(get())
            .build()
    }
}
