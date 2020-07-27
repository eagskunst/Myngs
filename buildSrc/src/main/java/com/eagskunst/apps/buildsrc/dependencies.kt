package com.eagskunst.apps.buildsrc

import org.gradle.api.artifacts.dsl.DependencyHandler

object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:4.0.1"
    const val ktlintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:9.2.1"
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.3.0-rc01"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val activity = "androidx.activity:activity-ktx:1.2.0-alpha06"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.0-alpha06"

        object Room {
            private const val version = "2.3.0-alpha02"
            const val common = "androidx.room:room-common:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val testing = "androidx.room:room-testing:$version"
        }

        object Lifecycle {
            private const val version = "2.2.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }


        object Test {
            const val core = "androidx.arch.core:core-testing:2.1.0"
            const val ktx = "androidx.test:core-ktx:1.2.0"
            const val rules = "androidx.test:rules:1.2.0"

            object Instrumentation {
                private const val version = "3.2.0"
                const val core = "androidx.test.espresso:espresso-core:$version"
                const val archCore = "androidx.arch.core:core-testing:2.1.0"
                const val espressoContrib = "androidx.test.espresso:espresso-contrib:$version"
                const val junit = "androidx.test.ext:junit:1.1.1"
                const val junit_ktx = "androidx.test.ext:junit-ktx:1.1.0"
            }
        }

        object Paging {
            private const val version = "2.1.2"
            const val runtime ="androidx.paging:paging-runtime:$version"
            const val common ="androidx.paging:paging-common:$version"
        }
    }

    object Test {
        const val jUnit = "junit:junit:4.13"
        const val hamcrest = "org.hamcrest:hamcrest-all:1.3"

        object Mockk {
            private const val version = "1.10.0"
            const val test = "io.mockk:mockk:$version"
            const val androidTest = "io.mockk:mockk-android:$version"
        }

        object Kakao {
            const val core = "com.agoda.kakao:kakao:2.3.3"
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

    object Google {
        const val material = "com.google.android.material:material:1.1.0"

        object ExoPlayer {
            private const val version = "2.11.7"
            const val core = "com.google.android.exoplayer:exoplayer-core:$version"
            const val ui = "com.google.android.exoplayer:exoplayer-ui:$version"
        }
    }

    object Epoxy {
        private const val version = "4.0.0-beta5"
        const val epoxy = "com.airbnb.android:epoxy:$version"
        const val dataBinding = "com.airbnb.android:epoxy-databinding:$version"
        const val processor = "com.airbnb.android:epoxy-processor:$version"
        const val paging = "com.airbnb.android:epoxy-paging:$version"

    }

    object Coil {
        private const val version = "0.11.0"
        const val coil = "io.coil-kt:coil:$version"
    }

}