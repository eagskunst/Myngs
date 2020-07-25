package com.eagskunst.apps.myngs.data_android.modules

import com.eagskunst.apps.myngs.data.services.AlbumService
import com.eagskunst.apps.myngs.data.services.SearchService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by eagskunst in 25/7/2020.
 */

val servicesModule = module {
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    factory(named("BaseUrl")) {
        "https://itunes.apple.com/"
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl(
                get<String>(named("BaseUrl"))
            )
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(SearchService::class.java)
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(AlbumService::class.java)
    }
}