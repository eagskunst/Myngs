package com.eagskunst.apps.buildsrc

object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:4.0.1"
    const val ktlintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:9.2.1"
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0-rc01"
        const val coreKtx = "androidx.core:core-ktx:1.3.0-rc01"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    }


    object Kotlin {
        private const val version = "1.3.72"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }
}