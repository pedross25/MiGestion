// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    /*ext {
        val hilt_version = "2.48"
        val hilt_navigation_compose_version = "1.0.0"
    }*/
    repositories {
        maven ( url = "https://jitpack.io")
        maven ( url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {

        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")

        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.5.21")
    }
}

plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id ("org.jetbrains.kotlin.kapt") version "1.9.0" apply false
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    id ("app.cash.sqldelight") version "2.0.0" apply false
}