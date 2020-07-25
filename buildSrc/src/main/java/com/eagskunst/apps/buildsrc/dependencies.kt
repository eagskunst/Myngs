package com.eagskunst.apps.buildsrc

import org.gradle.api.artifacts.dsl.DependencyHandler

object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:4.0.1"
    const val ktlintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:9.2.1"
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0-rc01"
        const val coreKtx = "androidx.core:core-ktx:1.3.0-rc01"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"

        object Room {
            private const val version = "2.3.0-alpha02"
            const val common = "androidx.room:room-common:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val testing = "androidx.room:room-testing:$version"
        }
    }


    object Kotlin {
        private const val version = "1.3.72"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Network {

        object Retrofit {
            private const val version = "2.7.1"
            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
        }

        object Moshi {
            private const val version = "1.9.2"
            const val moshi = "com.squareup.moshi:moshi-kotlin:$version"
            const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        }

        object OkHttp {
            private const val version = "4.2.2"
            const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
        }
    }

    object Coroutines {
        private const val version = "1.3.6"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Koin {
        private const val version = "2.1.5"
        const val core = "org.koin:koin-core:$version"
        const val android = "org.koin:koin-android:$version"
        const val androidScope = "org.koin:koin-android-scope:$version"
        const val androidViewModel = "org.koin:koin-android-viewmodel:$version"
        const val test = "org.koin:koin-test:$version"
    }

}